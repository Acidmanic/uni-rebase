package com.acidmanic.utility.svn2git.migration.commands;

import java.util.ArrayList;

import com.acidmanic.utility.svn2git.migration.MigrationCommand;
import com.acidmanic.utility.svn2git.models.CommitData;
import com.acidmanic.utility.svn2git.models.MigrationConfig;
import com.acidmanic.utility.svn2git.models.MigrationContext;
import com.acidmanic.utility.svn2git.models.SCId;
import com.acidmanic.utility.svn2git.services.HistoryHelper;
import com.acidmanic.utility.svn2git.services.SourceControlService;

public class MigrateCommits implements MigrationCommand {

    @Override
    public void execute( MigrationContext context) {

        SourceControlService source = context.getUpdateServiceBuilder().build(context.getUpdateRepoLocations().getQueryRootDir());
        SourceControlService destination=context.getCommitServiceBuilder().build(context.getCommitRepoLocations().getQueryRootDir());

        ArrayList<CommitData> allCommits;
        try {
            allCommits = source.listAllCommits();
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            return;
        }

        HistoryHelper.sort(allCommits);

        SCId fromId = context.getConfig().getLastCommitedId();
        
        int index =  HistoryHelper.skipToIndex(allCommits,fromId);

        for(int i=index;i<allCommits.size();i++){
            
            CommitData commit = allCommits.get(i);

           try {
                migrateSvn2Git(source,destination,commit,context.getConfig());
           } catch (Exception e) {
               //TODO: handle exception
               e=e;
           }
        }
        
    }


    private void migrateSvn2Git(SourceControlService svn 
                               ,SourceControlService git
                               ,CommitData commit
                               ,MigrationConfig config) throws Exception {

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
    }


}