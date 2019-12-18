package com.acidmanic.utility.unirebase.migration.commands;

import com.acidmanic.utility.unirebase.migration.MigrationCommand;
import com.acidmanic.utility.unirebase.models.MigrationContext;
import com.acidmanic.utility.unirebase.services.SourceControlService;

public class SetDestinationSourceController implements MigrationCommand {

    @Override
    public void execute(MigrationContext context) {

        SourceControlService destination=context.getCommitServiceBuilder().build(context.getCommitRepoLocations().getQueryRootDir());

        context.dataStorage().put(this.getClass(),destination);
        
	}

}