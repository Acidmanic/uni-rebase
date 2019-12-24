/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.utility.unirebase.services;

import com.acidmanic.utility.unirebase.models.MigrationConfig;
import com.acidmanic.utility.unirebase.models.MigrationContext;
import java.io.File;
import java.util.function.Consumer;

/**
 *
 * @author 80116
 */
public class MigrationContextBuilder {
    
    
    
    private Consumer<String> logger = (text) -> {};

    public MigrationContextBuilder() {
    }
    
    public MigrationContextBuilder(Consumer<String> logger) {
        this.logger = logger;
    }
    
    
    public MigrationContext createSvnToGitContext(String srcPath, String dstPath,MigrationConfig config) {

        MigrationContext context = createContext(srcPath, dstPath,config);

        context.setCommitServiceBuilder((dir) -> safeGitService(dir,config));

        context.setUpdateServiceBuilder((dir) -> safeSvnService(dir,config));

        return context;
    }
    
    public MigrationContext createGitToGitContext(String srcPath, String dstPath,MigrationConfig config) {

        MigrationContext context = createContext(srcPath, dstPath,config);

        context.setCommitServiceBuilder((dir) -> safeGitService(dir,config));

        context.setUpdateServiceBuilder((dir) -> safeSvnService(dir,config));

        return context;
    }
    
    
    

    private SourceControlService safeGitService(File dir,MigrationConfig config) {
        try {
            return new GitService(dir);
        } catch (Exception e) {
        }

        this.logger.accept("Unable to create git repository at:" + dir.toString());

        return null;
    }

    private SourceControlService safeSvnService(File dir,MigrationConfig config) {
        try {
            if (config.getUsername() != null) {
                return new SvnService(dir, config.getUsername(), config.getPassword());
            }
            return new SvnService(dir);
        } catch (Exception e) {
        }

        this.logger.accept("Unable to create SVN repository at:" + dir.toString());

        return null;
    }

    private MigrationContext createContext(String svnPath, String gitPath,MigrationConfig config) {
        MigrationContext context = new MigrationContext();

        context.setConfig(config);
        context.setDestinationDirectory(new File(gitPath));
        context.setSourceRepoLocations(Repository.svnRepositoryLocations(svnPath,
                 config.getSourcesDirectory()));
        context.setLogger(this.logger);

        return context;
    }
}
