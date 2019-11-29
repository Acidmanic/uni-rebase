package com.acidmanic.utility.unirebase.models;

import java.io.File;

public class RepositoryLocations {



    private File sourcesDir;
    private File queryRootDir;
    private File databseDir;

    public File getSourcesDir() {
        return sourcesDir;
    }

    public File getDatabseDir() {
        return databseDir;
    }

    public void setDatabseDir(File databseDir) {
        this.databseDir = databseDir;
    }

    public File getQueryRootDir() {
        return queryRootDir;
    }

    public void setQueryRootDir(File queryRootDir) {
        this.queryRootDir = queryRootDir;
    }

    public void setSourcesDir(File sourcesDir) {
        this.sourcesDir = sourcesDir;
    }


    
}