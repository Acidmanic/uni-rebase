package com.acidmanic.utility.unirebase.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class FilesystemService {

    public void syncInto(File src, File dst, List<String> ignoreList) throws Exception {

        ignoreList = normalize(ignoreList);

        dst.mkdirs();

        deleteRepoContents(dst, ignoreList);

        copyRepoContent(src, dst, ignoreList);

    }

    private void copyRepoContent(File src, File dst, List<String> ignoreList) throws Exception {
        
        File[] files = src.listFiles();

        for (File file : files) {

            if (!ignoreList.contains(file.getName().toLowerCase())) {

                copyInto(file, dst);

            }
        }
    }

    public void copyInto(File file, File dst) throws Exception {


        if(!file.isDirectory()){

            copySingleFileToDirectory(file, dst);

        } else {
            File[] subs = file.listFiles();

            File newBase = dst.toPath().resolve(file.getName()).toFile();

            newBase.mkdirs();

            for (File sub : subs)
                copyInto(sub, newBase);
        }

    }

    private void copySingleFileToDirectory(File file, File dstDir) throws Exception {

        Files.copy(file.toPath(), dstDir.toPath().resolve(file.getName()), StandardCopyOption.COPY_ATTRIBUTES);
    }

    private List<String> normalize(List<String> ignoreList) {

        List<String> ret = new ArrayList<>();

        for(String item : ignoreList){

            item = item.replace("\\", "/");

            item = item.toLowerCase();

            if(item.startsWith("./")){

                item= item.substring(2, item.length());
            }

            ret.add(item);
        }

        return ret;
    }

    private void deleteRepoContents(File dst, List<String> ignoreList) {

        File[] files = dst.listFiles();

        for(File file: files){

            if(!ignoreList.contains(file.getName().toLowerCase())){

                deleteAway(file);
            }
        }

    }

    public void deleteAway(File file) {

        if( file.isDirectory()){
            
            File[] files = file.listFiles();

            for(File f : files) deleteAway(f);

        }
        
        file.delete();
    }


    public void deleteContent(File dir, String[] ignoreList ){
        File[] subs = dir.listFiles();

        for(File sub:subs){
            if(!isIgnored(sub.getName(), ignoreList)){

                deleteAway(sub);
            }
        }
    }
   
    private boolean isIgnored(String name,String[] ignoreList){

        for(String item:ignoreList)

            if(item.compareTo(name)==0) return true;

        return false;
    }

	public void moveContent(File src, File dst) throws Exception {

        File[] files = src.listFiles();

        for(File sFile:files){

            File dFile = dst.toPath().resolve(sFile.getName()).toFile();

            if(sFile.isDirectory()){

                dFile.mkdirs();

                moveContent(sFile, dFile);
            }else{
                copySingleFileToDirectory(sFile,dst);
            }

            deleteAway(sFile);
        }

    }
    

    public boolean sameLocation(File file1, File file2) {

        String path1 = file1.toPath().toAbsolutePath().normalize().toString();
        String path2 = file2.toPath().toAbsolutePath().normalize().toString();

        return path1.compareTo(path2)==0;
    }


    public Path resolve(Path base,String...relative){
        Path ret = base;

        for(String rel:relative){

            ret = ret.resolve(rel);
        }

        return ret;
    }

    public File resolve(File base,String...relative){
        return resolve(base.toPath(), relative).toFile();
    }

	public void copyContent(File srcDir, File dstDir) throws Exception {
        
        File[] subs = srcDir.listFiles();

        for(File f : subs){

            if(f.isDirectory()){
                
                File dstF = dstDir.toPath().resolve(f.getName()).toFile();

                dstF.mkdirs();

                copyContent(f, dstF);
            }else{
                
                copySingleFileToDirectory(f, dstDir);
            }
        }
	}

}
