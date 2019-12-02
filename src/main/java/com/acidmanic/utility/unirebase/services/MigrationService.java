package com.acidmanic.utility.unirebase.services;

import java.io.File;
import java.util.function.Consumer;

import com.acidmanic.utility.unirebase.migrationstrategies.MigrationStrategy;
import com.acidmanic.utility.unirebase.models.MigrationConfig;
import com.acidmanic.utility.unirebase.models.MigrationContext;

public class MigrationService {

    private final MigrationConfig migrationConfig;
    private Consumer<String> logger = (text)->{};

    public void migrateSvn2Git(String svnPath, String gitPath) throws Exception {
     
        MigrationStrategy strategy = this.migrationConfig.getMigrationStrategy();

        MigrationContext context = createSvnToGitContext(svnPath, gitPath);

        strategy.execute(context);
    }


    private MigrationContext createSvnToGitContext(String svnPath, String gitPath){
        
        MigrationContext context = createContext(svnPath, gitPath);

        context.setCommitServiceBuilder((dir) -> safeGitService(dir));

        context.setUpdateServiceBuilder((dir) -> safeSvnService(dir));

        return context;

    }

    private SourceControlService safeGitService(File dir) {
        try {
            return new GitService(dir);
        } catch (Exception e) {}

        this.logger.accept("Unable to create git repository at:" + dir.toString());

        return null;
    }

    private SourceControlService safeSvnService(File dir) {
        try {
            if(this.migrationConfig.getUsername()!=null){
                return new SvnService(dir, this.migrationConfig.getUsername(), this.migrationConfig.getPassword());
            }
            return new SvnService(dir);
        } catch (Exception e) {}

        this.logger.accept("Unable to create SVN repository at:" + dir.toString());
        
        return null;
    }

    private MigrationContext createContext(String svnPath, String gitPath) {
        MigrationContext context = new MigrationContext();

        context.setConfig(this.migrationConfig);
        context.setDestinationDirectory(new File(gitPath));
        context.setSourceRepoLocations(Repository.svnRepositoryLocations(svnPath
                ,this.migrationConfig.getSourcesDirectory()));
        context.setLogger(this.logger);
        
        return context;
    }

    public MigrationService() {
        this.migrationConfig = MigrationConfig.Default;
    }


    public MigrationService(MigrationConfig migrationConfig) {
        this.migrationConfig = migrationConfig;
    }

    public MigrationConfig getMigrationConfig() {
        return migrationConfig;
    }

    public Consumer<String> getLogger() {
        return logger;
    }

    public void setLogger(Consumer<String> logger) {
        this.logger = logger;
    }


    
}
