package com.acidmanic.utility.svn2git.migration.commands;

import com.acidmanic.utility.svn2git.migration.MigrationCommand;
import com.acidmanic.utility.svn2git.models.MigrationContext;
import com.acidmanic.utility.svn2git.services.Repository;
import com.acidmanic.utility.svn2git.services.SourceControlService;

public class SetUpdateRepoToSvnRepoAtDestination implements MigrationCommand {

    @Override
    public void execute(MigrationContext context) {

        context.setUpdateRepoLocations(
            Repository.svnRepositoryLocations(
                context.getDestinationDirectory().toPath().toAbsolutePath().normalize().toString()
            )
        );
    }

}