package com.acidmanic.utility.svn2git.models;

import com.acidmanic.utility.svn2git.commitconversion.CommitRefiner;
import com.acidmanic.utility.svn2git.commitconversion.DefaultCommitRefiner;
import com.acidmanic.utility.svn2git.commitmessageformatter.CommitMessageFormatter;
import com.acidmanic.utility.svn2git.commitmessageformatter.DefaultCommitMessageFormatter;
import com.acidmanic.utility.svn2git.commitmessageformatter.StringCommitMessageFormatter;

public class MigrationConfig {


    public static final MigrationConfig Default = new MigrationConfig("trunk", new DefaultCommitRefiner(), new DefaultCommitMessageFormatter());



    public MigrationConfig() {
        this.username = null;
        this.password = null;
    }

    

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String sourcesDirectory;
    private CommitRefiner commitRefiner;
    private CommitMessageFormatter CommitMessageFormatter;
    private SCId lastCommitedId = SCId.FIRST_GIT_COMMIT;
    private String username = null;
    private String password = null;


    public String getSourcesDirectory() {
        return sourcesDirectory;
    }

    public void setSourcesDirectory(String sourcesDirectory) {
        this.sourcesDirectory = sourcesDirectory;
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

    public MigrationConfig(String sourcesDirectory, CommitRefiner commitRefiner, CommitMessageFormatter CommitMessageFormatter) {
        this.sourcesDirectory = sourcesDirectory;
        this.commitRefiner = commitRefiner;
        this.CommitMessageFormatter = CommitMessageFormatter;
    }


    public static MigrationConfig formatedMigrationConfig(String commitMessageFormat){
        return new MigrationConfig("trunk", new DefaultCommitRefiner(),new StringCommitMessageFormatter(commitMessageFormat));
    }

	public SCId getLastCommitedId() {
		return this.lastCommitedId;
    }

    public void setLastCommitedId(SCId lastCommitedId) {
        this.lastCommitedId = lastCommitedId;
    }
    
}
