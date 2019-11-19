package com.acidmanic.utility.svn2git.models;

import com.acidmanic.utility.svn2git.commitconversion.CommitRefiner;
import com.acidmanic.utility.svn2git.commitconversion.DefaultCommitRefiner;
import com.acidmanic.utility.svn2git.commitmessageformatter.CommitMessageFormatter;
import com.acidmanic.utility.svn2git.commitmessageformatter.DefaultCommitMessageFormatter;
import com.acidmanic.utility.svn2git.commitmessageformatter.StringCommitMessageFormatter;

public class MigrationConfig {


    public static final MigrationConfig Default = new MigrationConfig("trunk", new DefaultCommitRefiner(), new DefaultCommitMessageFormatter());



    



    public MigrationConfig() {
    }

    private String gitMasterSvnDirectory;
    private CommitRefiner commitRefiner;
    private CommitMessageFormatter CommitMessageFormatter;


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

    public CommitMessageFormatter getCommitMessageFormatter() {
        return CommitMessageFormatter;
    }

    public void setCommitMessageFormatter(CommitMessageFormatter commitMessageFormatter) {
        CommitMessageFormatter = commitMessageFormatter;
    }

    public MigrationConfig(String gitMasterSvnDirectory, CommitRefiner commitRefiner, CommitMessageFormatter CommitMessageFormatter) {
        this.gitMasterSvnDirectory = gitMasterSvnDirectory;
        this.commitRefiner = commitRefiner;
        this.CommitMessageFormatter = CommitMessageFormatter;
    }


    public static MigrationConfig formatedMigrationConfig(String commitMessageFormat){
        return new MigrationConfig("trunk", new DefaultCommitRefiner(),new StringCommitMessageFormatter(commitMessageFormat));
    }
    
}
