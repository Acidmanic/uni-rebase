package com.acidmanic.utility.unirebase.models;

import java.io.File;

import com.acidmanic.utility.unirebase.services.SourceControlServiceBuilder;

public class MigrationContext {

    private RepositoryLocations sourceRepoLocations;

    private RepositoryLocations updateRepoLocations;

    private RepositoryLocations commitRepoLocations;

    private File destinationDirectory;

    private MigrationConfig config;

    private SourceControlServiceBuilder updateServiceBuilder;

    private SourceControlServiceBuilder commitServiceBuilder;

    public RepositoryLocations getSourceRepoLocations() {
        return sourceRepoLocations;
    }

    public SourceControlServiceBuilder getCommitServiceBuilder() {
        return commitServiceBuilder;
    }

    public void setCommitServiceBuilder(SourceControlServiceBuilder commitServiceBuilder) {
        this.commitServiceBuilder = commitServiceBuilder;
    }

    public SourceControlServiceBuilder getUpdateServiceBuilder() {
        return updateServiceBuilder;
    }

    public void setUpdateServiceBuilder(SourceControlServiceBuilder updateServiceBuilder) {
        this.updateServiceBuilder = updateServiceBuilder;
    }

    public MigrationConfig getConfig() {
        return config;
    }

    public void setConfig(MigrationConfig config) {
        this.config = config;
    }

    public File getDestinationDirectory() {
        return destinationDirectory;
    }

    public void setDestinationDirectory(File destinationDirectory) {
        this.destinationDirectory = destinationDirectory;
    }

    public RepositoryLocations getCommitRepoLocations() {
        return commitRepoLocations;
    }

    public void setCommitRepoLocations(RepositoryLocations commitRepoLocations) {
        this.commitRepoLocations = commitRepoLocations;
    }

    public RepositoryLocations getUpdateRepoLocations() {
        return updateRepoLocations;
    }

    public void setUpdateRepoLocations(RepositoryLocations updateRepoLocations) {
        this.updateRepoLocations = updateRepoLocations;
    }

    public void setSourceRepoLocations(RepositoryLocations sourceRepoLocations) {
        this.sourceRepoLocations = sourceRepoLocations;
    }

    

}
