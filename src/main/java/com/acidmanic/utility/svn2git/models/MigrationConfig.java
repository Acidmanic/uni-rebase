package com.acidmanic.utility.svn2git.models;

import com.acidmanic.utility.svn2git.commitconversion.CommitRefiner;
import com.acidmanic.utility.svn2git.commitconversion.DefaultCommitRefiner;

public class MigrationConfig {


    public static final MigrationConfig Default = new MigrationConfig("trunk", new DefaultCommitRefiner());



    private MigrationConfig(String gitMasterSvnDirectory, CommitRefiner commitRefiner) {
        this.gitMasterSvnDirectory = gitMasterSvnDirectory;
        this.commitRefiner = commitRefiner;
    }



    public MigrationConfig() {
    }

    private String gitMasterSvnDirectory;
    private CommitRefiner commitRefiner;



    public String getGitMasterSvnDirectory() {
        return gitMasterSvnDirectory;
    }

    public void setGitMasterSvnDirectory(String gitMasterSvnDirectory) {
        this.gitMasterSvnDirectory = gitMasterSvnDirectory;
    }

    public CommitRefiner getCommitRefiner() {
        return commitRefiner;
    }

    public void setCommitRefiner(CommitRefiner commitRefiner) {
        this.commitRefiner = commitRefiner;
    }



    
}
