package com.acidmanic.utility.svn2git.models;


public class MigrationConfig {


    public static final MigrationConfig Default = new MigrationConfig("trunk");




    private MigrationConfig(String masterDirectory) {
        this.gitMasterSvnDirectory = masterDirectory;
    }



    public MigrationConfig() {
    }

    private String gitMasterSvnDirectory;

    public String getGitMasterSvnDirectory() {
        return gitMasterSvnDirectory;
    }

    public void setGitMasterSvnDirectory(String gitMasterSvnDirectory) {
        this.gitMasterSvnDirectory = gitMasterSvnDirectory;
    }



    
}
