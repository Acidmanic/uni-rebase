package com.acidmanic.utility.unirebase.models;

import com.acidmanic.utility.unirebase.commitconversion.CommitRefiner;
import com.acidmanic.utility.unirebase.commitconversion.DefaultCommitRefiner;
import com.acidmanic.utility.unirebase.commitmessageformatter.CommitMessageFormatter;
import com.acidmanic.utility.unirebase.commitmessageformatter.DefaultCommitMessageFormatter;
import com.acidmanic.utility.unirebase.commitmessageformatter.StringCommitMessageFormatter;
import com.acidmanic.utility.unirebase.migrationstrategies.InDestinationSvnToGitStrategy;
import com.acidmanic.utility.unirebase.migrationstrategies.MigrationStrategy;
import com.acidmanic.utility.unirebase.migrationstrategies.SafeMigrationStrategy;
import com.acidmanic.utility.unirebase.services.GitService;
import com.acidmanic.utility.unirebase.services.SourceControlService;

public class MigrationConfig {


    public static final MigrationConfig Default = new MigrationConfig("trunk",GitService.class, new DefaultCommitRefiner(), new DefaultCommitMessageFormatter());



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

    
    private Class<? extends SourceControlService> destinationRepositoryType;
    private String sourcesDirectory;
    private CommitRefiner commitRefiner;
    private CommitMessageFormatter CommitMessageFormatter;
    private SCId lastCommitedId = SCId.FIRST_GIT_COMMIT;
    private String username = null;
    private String password = null;
    private MigrationStrategy migrationStrategy = new SafeMigrationStrategy();
    private boolean forceClean = false;

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

    public MigrationConfig(String sourcesDirectory,Class<? extends SourceControlService> destinationType
            , CommitRefiner commitRefiner, CommitMessageFormatter CommitMessageFormatter) {
        this.destinationRepositoryType = destinationType;
        this.sourcesDirectory = sourcesDirectory;
        this.commitRefiner = commitRefiner;
        this.CommitMessageFormatter = CommitMessageFormatter;
    }


    public static MigrationConfig formatedMigrationConfig(String commitMessageFormat, Class<? extends SourceControlService> destination){
        return new MigrationConfig("trunk", destination, new DefaultCommitRefiner(),new StringCommitMessageFormatter(commitMessageFormat));
    }

    public static MigrationConfig formatedMigrationConfig(String commitMessageFormat){
        return new MigrationConfig("trunk", GitService.class, new DefaultCommitRefiner(),new StringCommitMessageFormatter(commitMessageFormat));
    }
	public SCId getLastCommitedId() {
		return this.lastCommitedId;
    }

    public void setLastCommitedId(SCId lastCommitedId) {
        this.lastCommitedId = lastCommitedId;
    }

    public Class<? extends SourceControlService> getDestinationRepositoryType() {
        return destinationRepositoryType;
    }

    public void setDestinationRepositoryType(Class<? extends SourceControlService> destinationRepositoryType) {
        this.destinationRepositoryType = destinationRepositoryType;
    }

    public MigrationStrategy getMigrationStrategy() {
        return migrationStrategy;
    }

    public void setMigrationStrategy(MigrationStrategy migrationStrategy) {
        this.migrationStrategy = migrationStrategy;
    }

    public boolean isForceClean() {
        return forceClean;
    }

    public void setForceClean(boolean forceClean) {
        this.forceClean = forceClean;
    }
    
    
}
