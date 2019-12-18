package com.acidmanic.utility.unirebase.migration.commands;

import java.util.List;

import com.acidmanic.utility.unirebase.migration.MigrationCommand;
import com.acidmanic.utility.unirebase.models.CommitData;
import com.acidmanic.utility.unirebase.models.MigrationContext;
import com.acidmanic.utility.unirebase.models.SCId;
import com.acidmanic.utility.unirebase.services.HistoryHelper;
import com.acidmanic.utility.unirebase.services.MigrationProgress;

public class FigureStartingCommit implements MigrationCommand {

    @Override
    public void execute(MigrationContext context) {

        List<CommitData> allCommits = context.dataStorage().get(GetAllCommits.class);

        SCId fromId = getLastCommit(context);
        
        int index =  HistoryHelper.skipToIndex(allCommits,fromId);

        context.dataStorage().put(this.getClass(), index);

        
    }
    

    private SCId getLastCommit(MigrationContext context) {
        
        MigrationProgress progress = new MigrationProgress(context.getProgressFile());

        SCId ret = progress.getLastCommit();

        if(ret != null){
            return ret;
        }
        return context.getConfig().getLastCommitedId();
    }

}