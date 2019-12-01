package com.acidmanic.utility.unirebase.migration.commands;

import java.util.ArrayList;

import com.acidmanic.utility.unirebase.migration.MigrationCommand;
import com.acidmanic.utility.unirebase.models.CommitData;
import com.acidmanic.utility.unirebase.models.MigrationConfig;
import com.acidmanic.utility.unirebase.models.MigrationContext;
import com.acidmanic.utility.unirebase.models.SCId;
import com.acidmanic.utility.unirebase.services.HistoryHelper;
import com.acidmanic.utility.unirebase.services.MigrationProgress;
import com.acidmanic.utility.unirebase.services.SourceControlService;
import java.io.File;
import java.util.List;

public class MigrateCommits implements MigrationCommand {

    @Override
    public void execute( MigrationContext context) {

        SourceControlService source = context.getUpdateServiceBuilder().build(context.getUpdateRepoLocations().getQueryRootDir());

        ArrayList<CommitData> allCommits;
        try {
            allCommits = source.listAllCommits();
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            return;
        }
        
        File progressFile = context.getProgressFile(); 
        
        MigrationProgress progress = new MigrationProgress(progressFile);
        
        SourceControlService destination=context.getCommitServiceBuilder().build(context.getCommitRepoLocations().getQueryRootDir());

        
        HistoryHelper.sort(allCommits);

        SCId fromId = getLastCommit(context,progress);
        
        int index =  HistoryHelper.skipToIndex(allCommits,fromId);

        for(int i=index;i<allCommits.size();i++){
            
            CommitData commit = allCommits.get(i);

           try {
                migrateSvn2Git(source,destination,commit,context.getConfig(),progress);
           } catch (Exception e) {
               e=e;
           }
        }
        
        source.dispose();
        
        destination.dispose();
        
    }


    private void migrateSvn2Git(SourceControlService svn 
                               ,SourceControlService git
                               ,CommitData commit
                               ,MigrationConfig config
                               ,MigrationProgress progress) throws Exception {

        SCId id = commit.getIdentifier();

        if(id.isEmpty()) {
            System.out.println("Wrn: skipped non-existing commit: " + id.toString());
            return;
        }

        svn.recallProjectState(id);

        commit = config.getCommitRefiner().refine(commit);

        commit.setMessage(
            config.getCommitMessageFormatter().format(commit)
        );

        git.acceptAllChanges(commit);
        
        progress.setLastCommit(commit.getIdentifier());
        
    }


    private SCId getLastCommit(MigrationContext context, MigrationProgress progress) {
        
        SCId ret = progress.getLastCommit();
        if(ret != null){
            return ret;
        }
        return context.getConfig().getLastCommitedId();
    }

}