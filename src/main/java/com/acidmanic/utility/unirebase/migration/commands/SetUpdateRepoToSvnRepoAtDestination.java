package com.acidmanic.utility.unirebase.migration.commands;

import com.acidmanic.utility.unirebase.migration.MigrationCommand;
import com.acidmanic.utility.unirebase.models.MigrationContext;
import com.acidmanic.utility.unirebase.services.Repository;

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