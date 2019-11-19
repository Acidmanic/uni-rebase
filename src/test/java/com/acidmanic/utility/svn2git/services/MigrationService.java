package com.acidmanic.utility.svn2git.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.acidmanic.utility.svn2git.models.CommitData;

import org.tmatesoft.svn.core.SVNLogEntry;

public class MigrationService {




    private final List<String> IGNORELIST;

    


    public void migrate(String svnPath,String gitPath) throws Exception {
        

        GitService gitService = new GitService(gitPath);

        SvnService svnService = new SvnService(svnPath);

        migrate(svnService, gitService);
    }

    public void migrate(String svnPath,String gitPath,String svnUsername, String svnPassword) throws Exception {
        

        GitService gitService = new GitService(gitPath);

        SvnService svnService = new SvnService(svnPath,svnUsername,svnPassword);

        migrate(svnService, gitService);
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

        long revision = entry.getRevision();

        svn.updateToRevision(revision);
        
        FilesystemService fs = new FilesystemService();

        fs.syncInto(svn.getRootDirectory(), git.getRootDirectory(), this.IGNORELIST);

        git.addAll();

        CommitData commit = getCommitData(entry);

        git.commit(commit);

    }

    public static CommitData getCommitData(SVNLogEntry entry) {
        CommitData commit = new CommitData();

        commit.setAuthorEmail(entry.getAuthor() + "@acidmanic.com");

        commit.setAuthorName(entry.getAuthor());

        commit.setDate(entry.getDate());

        commit.setMessage(entry.getMessage());

        return commit;
    }

    public MigrationService() {
        this.IGNORELIST = new ArrayList<>();
        this.IGNORELIST.add(".git");
        this.IGNORELIST.add(".svn");
    }
}
