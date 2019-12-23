package com.acidmanic.utility.unirebase.migration.commands;

import java.util.ArrayList;

import com.acidmanic.utility.unirebase.migration.MigrationCommand;
import com.acidmanic.utility.unirebase.models.CommitData;
import com.acidmanic.utility.unirebase.models.MigrationConfig;
import com.acidmanic.utility.unirebase.models.MigrationContext;
import com.acidmanic.utility.unirebase.models.SCId;
import com.acidmanic.utility.unirebase.services.FilesystemService;
import com.acidmanic.utility.unirebase.services.HistoryHelper;
import com.acidmanic.utility.unirebase.services.MigrationProgress;
import static com.acidmanic.utility.unirebase.services.Repository.*;
import com.acidmanic.utility.unirebase.services.SourceControlService;
import java.io.File;


@Deprecated("Due to be fat class and violating SRP, this class is deprecated, use MigrateCommits instead")
public class MigrateCommitsSingleCommand implements MigrationCommand {

    
    private static final String[] SC_DB_DIRS={DBDIR_GIT,DBDIR_SVN};
    
    
    @Override
    public void execute( MigrationContext context) {

        SourceControlService source = context.getUpdateServiceBuilder().build(context.getUpdateRepoLocations().getQueryRootDir());

        ArrayList<CommitData> allCommits;
        try {
            allCommits = source.listAllCommits();
        } catch (Exception e1) {
            context.getLogger().accept("Problem getting commits list: " + e1.getMessage());
            return;
        }
        
        File progressFile = context.getProgressFile(); 
        
        MigrationProgress progress = new MigrationProgress(progressFile);
        
        SourceControlService destination=context.getCommitServiceBuilder().build(context.getCommitRepoLocations().getQueryRootDir());

        
        

        SCId fromId = getLastCommit(context,progress);
        
        int index =  HistoryHelper.skipToIndex(allCommits,fromId);

        for(int i=index;i<allCommits.size();i++){
            
            CommitData commit = allCommits.get(i);

           try {
                migrateSvn2Git(source,destination,commit,context,progress);
           } catch (Exception e) {
               context.getLogger().accept("Problem migrating commit: " + e.getMessage());
           }
        }
        
        source.dispose();
        
        destination.dispose();
        
    }


    private void migrateSvn2Git(SourceControlService src 
                               ,SourceControlService dst
                               ,CommitData commit
                               ,MigrationContext context
                               ,MigrationProgress progress) throws Exception {

        SCId id = commit.getIdentifier();

        if(id.isEmpty()) {
            context.getLogger().accept("Wrn: skipped non-existing commit: " + id.toString());
            return;
        }

        src.recallProjectState(id);
        
        syncIfNotTheSame(context);

        MigrationConfig config = context.getConfig();
        
        commit = config.getCommitRefiner().refine(commit);

        commit.setMessage(
            config.getCommitMessageFormatter().format(commit)
        );

        dst.acceptAllChanges(commit);
        
        progress.setLastCommit(commit.getIdentifier());
        
    }


    private SCId getLastCommit(MigrationContext context, MigrationProgress progress) {
        
        SCId ret = progress.getLastCommit();
        if(ret != null){
            return ret;
        }
        return context.getConfig().getLastCommitedId();
    }

    private void syncIfNotTheSame(MigrationContext context) {
        FilesystemService fs = new FilesystemService();
        File src = context.getUpdateRepoLocations().getSourcesDir();
        File dst = context.getCommitRepoLocations().getSourcesDir();
        
        if(fs.sameLocation(src, dst)){
            return;
        }

        try {
            fs.deleteContent(dst,SC_DB_DIRS);
        } catch (Exception ex) {
            context.getLogger().accept("Problem clearing destination: " + ex.getMessage());
        }
        
        try {
            fs.copyContent(src, dst,SC_DB_DIRS);
        } catch (Exception ex) {
            context.getLogger().accept("Problem copying files: " + ex.getMessage());
        }
    }

}