package com.acidmanic.utility.unirebase.migration.commands;

import com.acidmanic.utility.unirebase.migration.MigrationCommand;
import com.acidmanic.utility.unirebase.models.MigrationContext;
import com.acidmanic.utility.unirebase.models.RepositoryLocations;
import com.acidmanic.utility.unirebase.services.Repository;
import com.acidmanic.utility.unirebase.services.SourceControlService;

public class SetCommitRepoToGitOnSvnAtDestination implements MigrationCommand {

    @Override
    public void execute(MigrationContext context) {


        String destinationDirectory = context.getDestinationDirectory().toPath().toAbsolutePath().toString();

        String trunk = context.getConfig().getSourcesDirectory();

        RepositoryLocations commitRepo = Repository.gitOnSvn(destinationDirectory,trunk);

        context.setCommitRepoLocations(commitRepo);
    }

}