package com.acidmanic.utility.svn2git.migration.commands;

import com.acidmanic.utility.svn2git.migration.MigrationCommand;
import com.acidmanic.utility.svn2git.models.MigrationContext;
import com.acidmanic.utility.svn2git.models.RepositoryLocations;
import com.acidmanic.utility.svn2git.services.Repository;
import com.acidmanic.utility.svn2git.services.SourceControlService;

public class SetCommitRepoToGitOnSvnAtDestination implements MigrationCommand {

    @Override
    public void execute(MigrationContext context) {


        String destinationDirectory = context.getDestinationDirectory().toPath().toAbsolutePath().toString();

        String trunk = context.getConfig().getSourcesDirectory();

        RepositoryLocations commitRepo = Repository.gitOnSvn(destinationDirectory,trunk);

        context.setCommitRepoLocations(commitRepo);
    }

}