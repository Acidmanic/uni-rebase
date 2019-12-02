package com.acidmanic.utility.unirebase.services;

import java.io.File;
import java.nio.file.Path;

import com.acidmanic.utility.unirebase.models.RepositoryLocations;
import java.util.ArrayList;
import java.util.List;

public class Repository {



    public static RepositoryLocations gitRepositoryLocations(String rootDirectory){
        return getRepositoryLocations(rootDirectory,".git");
    }

    public static RepositoryLocations svnRepositoryLocations(String rootDirectory){
        return getRepositoryLocations(rootDirectory,".svn");
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
        
        File svnDb = root.toPath().resolve(".svn").toFile();
        File gitDb = root.toPath().resolve(".git").toFile();
        
        if(svnDb.exists()){
            ret.add(SvnService.class);
        }
        
        if(gitDb.exists()){
            ret.add(GitService.class);
        }
        
        return ret;
    }

}