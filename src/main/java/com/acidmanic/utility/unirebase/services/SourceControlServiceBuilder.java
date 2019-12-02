package com.acidmanic.utility.unirebase.services;

import java.io.File;

public interface SourceControlServiceBuilder {

    public static final SourceControlServiceBuilder NULL = (rootDir) -> new NullSourceControlService(rootDir);
    
    SourceControlService build(File rootDir);
    
}