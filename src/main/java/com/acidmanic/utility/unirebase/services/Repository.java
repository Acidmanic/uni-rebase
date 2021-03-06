package com.acidmanic.utility.unirebase.services;

import java.io.File;
import java.nio.file.Path;

import com.acidmanic.utility.unirebase.models.RepositoryLocations;
import java.util.ArrayList;
import java.util.List;

public class Repository {

    public static final String DBDIR_SVN = ".svn";
    public static final String DBDIR_GIT = ".git";
    


    public static RepositoryLocations gitRepositoryLocations(String rootDirectory){
        return getRepositoryLocations(rootDirectory,DBDIR_GIT);
    }

    public static RepositoryLocations svnRepositoryLocations(String rootDirectory){
        return getRepositoryLocations(rootDirectory,DBDIR_SVN);
    }
    
    public static RepositoryLocations svnRepositoryLocations(String rootDirectory,String trunk){
        RepositoryLocations ret = getRepositoryLocations(rootDirectory,DBDIR_SVN);
        
        ret.setSourcesDir(ret.getSourcesDir().toPath().resolve(trunk).toFile());
        
        return ret;
    }

    public static RepositoryLocations gitOnSvn(String rootDirectory,String trunk){

        Path root = new File(rootDirectory).toPath().toAbsolutePath().normalize()
            .resolve(trunk);

        return gitRepositoryLocations(root.toString());

    }


    private static RepositoryLocations getRepositoryLocations(String rootDirectory,String...databaseSub){
        
        RepositoryLocations ret = new RepositoryLocations();

        File root = new File(rootDirectory).toPath().toAbsolutePath().normalize().toFile();

        ret.setQueryRootDir(root);

        FilesystemService fs = new FilesystemService();

        ret.setDatabseDir(fs.resolve(root, databaseSub));

        ret.setSourcesDir(root);

        return ret;
    }


    public static List<Class<? extends SourceControlService>> listPresentRepositories(File root){
        List<Class<? extends SourceControlService>> ret = new ArrayList<>();
        
        File svnDb = root.toPath().resolve(DBDIR_SVN).toFile();
        File gitDb = root.toPath().resolve(DBDIR_GIT).toFile();
        
        if(svnDb.exists()){
            ret.add(SvnService.class);
        }
        
        if(gitDb.exists()){
            ret.add(GitService.class);
        }
        
        return ret;
    }

}