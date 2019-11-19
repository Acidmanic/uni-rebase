package com.acidmanic.utility.svn2git.services;

import java.io.File;
import java.util.ArrayList;

import com.acidmanic.utility.svn2git.commitconversion.SvnLogEntryCommitConvertor;
import com.acidmanic.utility.svn2git.exceptions.NotImplementedYetException;
import com.acidmanic.utility.svn2git.models.CommitData;
import com.acidmanic.utility.svn2git.models.SCId;

import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNProperty;
import org.tmatesoft.svn.core.SVNPropertyValue;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.DefaultSVNRepositoryPool;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNLogClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;

public class SvnService implements SourceControlService {

    private SVNLogClient logClient;

    private SVNUpdateClient updateClient;

    private File repoFile;

    private SVNURL repoUrl;

    private DefaultSVNRepositoryPool repositoryPool;

    private ISVNAuthenticationManager authenticationManager;

    private SVNClientManager clientManager;

    private ISVNOptions svnOptions;

    private SvnLogEntryCommitConvertor commitConvertor = new SvnLogEntryCommitConvertor();

    public SvnService(File repoFile) throws Exception {
        this.repoFile = repoFile;

        this.repoUrl = SVNURL.fromFile(repoFile);

        this.repositoryPool = new DefaultSVNRepositoryPool(null, null);

        this.repositoryPool.createRepository(repoUrl, true);

        this.updateClient = new SVNUpdateClient(repositoryPool, new DefaultSVNOptions());

        this.logClient = new SVNLogClient(repositoryPool, new DefaultSVNOptions());

        this.svnOptions = new DefaultSVNOptions();

    }

    public SvnService(String repositoryPath) throws Exception {
        this(new File(repositoryPath));
    }

    public SvnService(File repoFile, String username, String password) throws Exception {
        this(repoFile);

        this.authenticationManager = new BasicAuthenticationManager(username, password);

        this.repositoryPool.setAuthenticationManager(this.authenticationManager);

        this.clientManager = SVNClientManager.newInstance(this.svnOptions, this.authenticationManager);

    }

    public SvnService(String repositoryPath, String username, String password) throws Exception {

        this(repositoryPath);

        this.authenticationManager = new BasicAuthenticationManager(username, password);

        this.repositoryPool.setAuthenticationManager(this.authenticationManager);

        this.clientManager = SVNClientManager.newInstance(this.svnOptions, this.authenticationManager);
    }

    public void updateToRevision(long revision) throws Exception {
        this.updateToRevision(this.repoFile, this.updateClient, revision);
    }

    private void updateToRevision(File repoFile, SVNUpdateClient updateClient, long revision) throws SVNException {
        clearSVNDirectory(repoFile);

        updateClient.doUpdate(repoFile, SVNRevision.create(revision), SVNDepth.INFINITY, true, true);

        System.out.println("Updated to " + revision + "'th' revision");
    }

    private void clearSVNDirectory(File repoFile) {
    }

    public ArrayList<CommitData> listAllCommits() throws Exception {

        ArrayList<SVNLogEntry> allEntries = new ArrayList<>();

        logClient.doLog(new File[] { repoFile }, SVNRevision.BASE, null, null, false, false, 1000000,
                (logEntry) -> allEntries.add(logEntry));

        ArrayList<CommitData> ret = new ArrayList<>();

        for(SVNLogEntry entry : allEntries){
            ret.add(
                this.commitConvertor.convert(entry)
            );
        }

        return ret;

    }

    @Override
    public File getRootDirectory() {
        return this.repoFile;
    }

    public void dispose() {

        repositoryPool.shutdownConnections(true);
    }

    public void ignore(File file) throws Exception {
        File dir = file.getParentFile().getAbsoluteFile();

        this.clientManager.getWCClient().doSetProperty(dir, SVNProperty.IGNORE, SVNPropertyValue.create(file.getName()),
                false, SVNDepth.EMPTY, null, new ArrayList<>());
    }

    @Override
    public void recallProjectState(SCId id) throws Exception {
        updateToRevision(id.getSvnRevision());
    }

    @Override
    public void acceptAllChanges(String commitMessage) throws Exception {
        throw new NotImplementedYetException();
    }

    @Override
    public void acceptAllChanges(CommitData commit) throws Exception {
        throw new NotImplementedYetException();
    }

    @Override
    public void initialize() throws Exception {
        throw new NotImplementedYetException();
    }

    @Override
    public boolean isRepo() {
        return this.repoFile.toPath().resolve(".svn").toFile().exists();
    }
}
