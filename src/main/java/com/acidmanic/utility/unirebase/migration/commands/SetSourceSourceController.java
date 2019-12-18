package com.acidmanic.utility.unirebase.migration.commands;

import com.acidmanic.utility.unirebase.migration.MigrationCommand;
import com.acidmanic.utility.unirebase.models.MigrationContext;
import com.acidmanic.utility.unirebase.services.SourceControlService;

public class SetSourceSourceController implements MigrationCommand{

	@Override
	public void execute(MigrationContext context) {
        SourceControlService source = context.getUpdateServiceBuilder().build(context.getUpdateRepoLocations().getQueryRootDir());
        
        context.dataStorage().put(this.getClass(),source);
	}



}