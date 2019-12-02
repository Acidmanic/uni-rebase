package com.acidmanic.utility.unirebase.migrationstrategies;

import com.acidmanic.utility.unirebase.migration.commands.CopySourceRepoDbToDestinationDirectory;
import com.acidmanic.utility.unirebase.migration.commands.MigrateCommits;
import com.acidmanic.utility.unirebase.migration.commands.SetCommitRepoToGitOnSvnAtDestination;
import com.acidmanic.utility.unirebase.migration.commands.SetUpdateRepoToSvnRepoAtDestination;

public class InDestinationSvnToGitStrategy extends MigrationStrategy {

    @Override
    protected void onConfigureSteps(StrategyBuilder builder) {
        builder.first(SetUpdateRepoToSvnRepoAtDestination.class)

        .then(SetCommitRepoToGitOnSvnAtDestination.class)

        .then(CopySourceRepoDbToDestinationDirectory.class)

        .then(MigrateCommits.class);
    }

}