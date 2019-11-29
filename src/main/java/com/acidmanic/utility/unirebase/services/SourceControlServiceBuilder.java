package com.acidmanic.utility.unirebase.services;

import java.io.File;

public interface SourceControlServiceBuilder {

    SourceControlService build(File rootDir);
    
}