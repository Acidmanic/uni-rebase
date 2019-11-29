package com.acidmanic.utility.svn2git.services;

import java.io.File;

public interface SourceControlServiceBuilder {

    SourceControlService build(File rootDir);
    
}