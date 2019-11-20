package com.acidmanic.utility.svn2git.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.acidmanic.utility.svn2git.models.CommitData;
import com.acidmanic.utility.svn2git.models.MigrationConfig;
import com.acidmanic.utility.svn2git.models.SCId;

public class MigrationService {




    private final List<String> IGNORELIST;

    private final MigrationConfig migrationConfig;


    public void migrateSvn2Git(String svnPath,String gitPath, SCId fromId) throws Exception {
        
        SourceControlService gitService =  new GitService(gitPath);

        SourceControlService svnService = new SvnService(svnPath);

        migrateSvn2Git(svnService, gitService, fromId);
    }

  

    public void migrateSvn2Git(String svnPath, String gitPath, SCId fromId, String svnUsername, String svnPassword) throws Exception {
    
        File srcSvnRepo = new File(svnPath);

        File srcDotSvnDir = srcSvnRepo.toPath().resolve(".svn").toFile();

        File migrationDirectory = new File(gitPath);// destination

        File migrationDotSvn = migrationDirectory.toPath().resolve(".svn").toFile();
        
        if(!migrationDotSvn.exists()) migrationDotSvn.mkdirs();
        
        FilesystemService fs = new FilesystemService();

        fs.syncInto(srcDotSvnDir, migrationDotSvn, new ArrayList<>());

        File gitMigrationDirectory = migrationDirectory.toPath().resolve(this.migrationConfig.getGitMasterSvnDirectory()).toFile();

        GitService gitService = new GitService(gitMigrationDirectory);

        SvnService svnService = new SvnService(migrationDirectory,svnUsername,svnPassword);

        migrateSvn2Git(svnService, gitService, fromId);

        fs.deleteContent(migrationDirectory, new String[]{this.migrationConfig.getGitMasterSvnDirectory()});

        fs.moveContent(gitMigrationDirectory,migrationDirectory);

        fs.deleteAway(gitMigrationDirectory);
        
    }



    private void migrateSvn2Git(SourceControlService svn, SourceControlService git, SCId fromId) throws Exception {

        ArrayList<CommitData> allCommits =  svn.listAllCommits();

        HistoryHelper.sort(allCommits);
        
        int index =  HistoryHelper.skipToIndex(allCommits,fromId);

        for(int i=index;i<allCommits.size();i++){
            
            CommitData commit = allCommits.get(i);

           migrateSvn2Git(svn,git,commit);
        }
    }

    private void migrateSvn2Git(SourceControlService svn,SourceControlService git, CommitData commit) throws Exception {

        SCId id = commit.getIdentifier();

        if(id.isEmpty()) {
            System.out.println("Wrn: skipped non-existing commit: " + id.toString());
            return;
        }

        svn.recallProjectState(id);

        commit = migrationConfig.getCommitRefiner().refine(commit);

        formatMessage(commit);

        git.acceptAllChanges(commit);
    }

    private void formatMessage(CommitData commit) {
        
        String message = this.migrationConfig.getCommitMessageFormatter().format(commit);
        
        commit.setMessage(message);
    }

    public MigrationService() {
        this.IGNORELIST = new ArrayList<>();
        initiateIgnoreList();
        this.migrationConfig = MigrationConfig.Default;
    }

    private void initiateIgnoreList() {
        
        this.IGNORELIST.add(".git");
        this.IGNORELIST.add(".svn");
    }


    public MigrationService(MigrationConfig migrationConfig) {
        this.IGNORELIST = new ArrayList<>();
        initiateIgnoreList();
        this.migrationConfig = migrationConfig;
    }


}
