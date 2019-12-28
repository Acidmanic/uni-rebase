/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.utility.unirebase.migration.commands;

import com.acidmanic.utility.unirebase.migration.MigrationCommand;
import com.acidmanic.utility.unirebase.models.CommitData;
import com.acidmanic.utility.unirebase.models.MigrationConfig;
import com.acidmanic.utility.unirebase.models.MigrationContext;
import com.acidmanic.utility.unirebase.models.SCId;
import com.acidmanic.utility.unirebase.services.FilesystemService;
import com.acidmanic.utility.unirebase.services.MigrationProgress;
import com.acidmanic.utility.unirebase.services.PreCommitHelperToolset;
import static com.acidmanic.utility.unirebase.services.Repository.DBDIR_GIT;
import static com.acidmanic.utility.unirebase.services.Repository.DBDIR_SVN;
import com.acidmanic.utility.unirebase.services.SafeRunner;
import com.acidmanic.utility.unirebase.services.SourceControlService;
import java.io.File;
import java.util.List;

/**
 *
 * @author 80116
 */
public class LoopThroughAndConvert implements MigrationCommand{

    
    
    private static final String[] SC_DB_DIRS={DBDIR_GIT,DBDIR_SVN};
    private final PreCommitHelperToolset helperToolset = new PreCommitHelperToolset();
    
    @Override
    public void execute(MigrationContext context) {
        SourceControlService src = context.dataStorage().get(SetSourceSourceController.class);
        
        SourceControlService dst = context.dataStorage().get(SetDestinationSourceController.class);
        
        MigrationProgress progress = new MigrationProgress(context.getProgressFile());
        
        List<CommitData> commits = context.dataStorage().get(GetAllCommits.class);
        
        SafeRunner runner = new SafeRunner().describe("[ERROR] Migrating Commit.").log(context.getLogger());
        
        commits.forEach( commit -> runner.run(()->migrateSingleCommit(src, dst, commit, context, progress)));
    }
    
    private void migrateSingleCommit(SourceControlService src 
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

        config.getPreCommitAction().onPreCommit(this.helperToolset, 
                context.getCommitRepoLocations().getSourcesDir(), 
                context.getLogger(), commit, config.getCommitMessageFormatter());
        
        dst.acceptAllChanges(commit);
        
        progress.setLastCommit(commit.getIdentifier());
        
    }
    
    private void syncIfNotTheSame(MigrationContext context) {
        
        File src = context.getUpdateRepoLocations().getSourcesDir();

        File dst = context.getCommitRepoLocations().getSourcesDir();
        
         new SafeRunner().describe("Problem Syncing " + src.toString())
                 .log(context.getLogger())
                 .run(()-> new FilesystemService().sync(src, dst, SC_DB_DIRS));
    }
    
}
