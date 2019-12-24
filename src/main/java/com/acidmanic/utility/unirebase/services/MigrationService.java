package com.acidmanic.utility.unirebase.services;

import java.util.function.Consumer;

import com.acidmanic.utility.unirebase.migrationstrategies.MigrationStrategy;
import com.acidmanic.utility.unirebase.models.MigrationConfig;
import com.acidmanic.utility.unirebase.models.MigrationContext;

public class MigrationService {

    private final MigrationConfig migrationConfig;
    private Consumer<String> logger = (text) -> {
    };

    public void migrateSvn2Git(String srcPath, String dstPath) throws Exception {

        MigrationContext context = new MigrationContextBuilder(this.logger)
                .createSvnToGitContext(srcPath, dstPath,this.migrationConfig);

        migrate(context);
    }

    public void migrateGit2Git(String srcPath, String dstPath) throws Exception {
       
       MigrationContext context = new MigrationContextBuilder(this.logger)
                .createGitToGitContext(srcPath, dstPath,this.migrationConfig);
        
        migrate(context);
    }

    private void migrate( MigrationContext context) {
        MigrationStrategy strategy = this.migrationConfig.getMigrationStrategy();

        strategy.setLogger(this.logger);

        strategy.execute(context);
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
