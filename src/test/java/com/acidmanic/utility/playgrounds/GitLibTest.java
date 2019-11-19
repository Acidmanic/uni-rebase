package com.acidmanic.utility.playgrounds;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;

public class GitLibTest {

    public static void main(String[] args) throws Exception {
        


        File repoFile = new File("output.git");


        if(!repoFile.exists()){
            repoFile.mkdirs();
        }

        Git.init().setDirectory(repoFile).call();

        System.out.println("Repository created");
    }
}
