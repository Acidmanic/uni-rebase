package com.acidmanic.utility.svn2git.migrationstrategies;

import java.util.List;

import com.acidmanic.utility.svn2git.migration.MigrationCommand;
import com.acidmanic.utility.svn2git.migration.commands.CopySourceRepoDbToDestinationDirectory;
import com.acidmanic.utility.svn2git.migration.commands.MigrateCommits;
import com.acidmanic.utility.svn2git.migration.commands.SetCommitRepoToGitOnSvnAtDestination;
import com.acidmanic.utility.svn2git.migration.commands.SetUpdateRepoToSvnRepoAtDestination;

public class InDestinationSvnToGitStrategy extends MigrationStrategy {

    @Override
    protected void onConfigureSteps(List<Class<? extends MigrationCommand>> steps) {

        steps.add(SetUpdateRepoToSvnRepoAtDestination.class);

        steps.add(SetCommitRepoToGitOnSvnAtDestination.class);

        steps.add(CopySourceRepoDbToDestinationDirectory.class);

        steps.add(MigrateCommits.class);

    }

}