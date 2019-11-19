package com.acidmanic.utility.svn2git.services;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.acidmanic.utility.svn2git.commitconversion.CommitConvertor;
import com.acidmanic.utility.svn2git.commitconversion.SvnLogEntryCommitConvertor;
import com.acidmanic.utility.svn2git.models.CommitData;
import com.acidmanic.utility.svn2git.models.MigrationConfig;

import org.tmatesoft.svn.core.SVNLogEntry;

public class MigrationService {




    private final List<String> IGNORELIST;

    private final CommitConvertor<SVNLogEntry> commitConvertor 
                = new SvnLogEntryCommitConvertor("acidmanic.com");

    private final MigrationConfig migrationConfig;







    public void migrate(String svnPath,String gitPath) throws Exception {
        

        GitService gitService =  new GitService(gitPath);

        SvnService svnService = new SvnService(svnPath);

        migrate(svnService, gitService);
    }

  

    public void migrate(String svnPath, String gitPath, String svnUsername, String svnPassword) throws Exception {
        

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

        migrate(svnService, gitService);

        fs.deleteContent(migrationDirectory, new String[]{this.migrationConfig.getGitMasterSvnDirectory()});

        fs.moveContent(gitMigrationDirectory,migrationDirectory);

        fs.deleteAway(gitMigrationDirectory);
        
    }



    private void migrate(SvnService svn, GitService git) throws Exception {

        ArrayList<SVNLogEntry> allEntries =  svn.listAllCommits();

        

        allEntries.sort(new Comparator<SVNLogEntry>() {

            @Override
            public int compare(SVNLogEntry o1, SVNLogEntry o2) {
                return (int) (o1.getRevision() - o2.getRevision());
            }
        });
        
        for(int i=0;i<allEntries.size();i++){
            
            SVNLogEntry entry = allEntries.get(i);

           migrate(svn,git,entry);
        }
    }

    private void migrate(SvnService svn, GitService git, SVNLogEntry entry) throws Exception {

        if(entry.getRevision() ==0 ){
            System.out.println("Wrn: skipped revision 0 (non-existing)");
            return;
        }

        long revision = entry.getRevision();

        svn.updateToRevision(revision);
        
        

        

        

        git.addAll();

        CommitData commit = this.commitConvertor.convert(entry);

        commit = migrationConfig.getCommitRefiner().refine(commit);

        formatMessage(commit);
        
        git.commit(commit);

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
