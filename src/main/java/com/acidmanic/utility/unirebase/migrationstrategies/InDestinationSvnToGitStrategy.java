package com.acidmanic.utility.unirebase.migrationstrategies;

import java.util.List;

import com.acidmanic.utility.unirebase.migration.MigrationCommand;
import com.acidmanic.utility.unirebase.migration.commands.CleanTargetDirectory;
import com.acidmanic.utility.unirebase.migration.commands.CopySourceRepoDbToDestinationDirectory;
import com.acidmanic.utility.unirebase.migration.commands.MigrateCommits;
import com.acidmanic.utility.unirebase.migration.commands.SetCommitRepoToGitOnSvnAtDestination;
import com.acidmanic.utility.unirebase.migration.commands.SetUpdateRepoToSvnRepoAtDestination;

public class InDestinationSvnToGitStrategy extends MigrationStrategy {

    @Override
    protected void onConfigureSteps(List<Class<? extends MigrationCommand>> steps) {

        steps.add(CleanTargetDirectory.class);
        
        steps.add(SetUpdateRepoToSvnRepoAtDestination.class);

        steps.add(SetCommitRepoToGitOnSvnAtDestination.class);

        steps.add(CopySourceRepoDbToDestinationDirectory.class);

        steps.add(MigrateCommits.class);

    }

}