package com.acidmanic.utility.svn2git.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
            Files.copy(file.toPath(), dst.toPath().resolve(file.getName()), StandardCopyOption.COPY_ATTRIBUTES);
        }else{
            File[] subs = file.listFiles();

            File newBase = dst.toPath().resolve(file.getName()).toFile();

            for(File sub:subs) copyInto(sub, newBase);
        }

        
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

        }else{
            file.delete();
        }
    }

   

}
