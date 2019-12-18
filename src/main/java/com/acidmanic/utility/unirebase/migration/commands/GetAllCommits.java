package com.acidmanic.utility.unirebase.migration.commands;

import java.util.ArrayList;

import com.acidmanic.utility.unirebase.migration.MigrationCommand;
import com.acidmanic.utility.unirebase.models.CommitData;
import com.acidmanic.utility.unirebase.models.MigrationContext;
import com.acidmanic.utility.unirebase.services.HistoryHelper;
import com.acidmanic.utility.unirebase.services.SourceControlService;

public class GetAllCommits implements MigrationCommand {

    @Override
    public void execute(MigrationContext context) {
        
        SourceControlService source = context.dataStorage().get(SetSourceSourceController.class);

        ArrayList<CommitData> allCommits;
        try {
            allCommits = source.listAllCommits();

            HistoryHelper.sort(allCommits);

            context.dataStorage().put(this.getClass(), allCommits);
        } catch (Exception e1) {
            context.getLogger().accept("Problem getting commits list: " + e1.getMessage());
            return;
        }

	}

}