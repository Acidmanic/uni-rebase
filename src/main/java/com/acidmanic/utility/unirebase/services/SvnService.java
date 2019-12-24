package com.acidmanic.utility.unirebase.services;

import java.io.File;
import java.util.ArrayList;

import com.acidmanic.utility.unirebase.commitconversion.SvnLogEntryCommitConvertor;
import com.acidmanic.utility.unirebase.exceptions.NotImplementedYetException;
import com.acidmanic.utility.unirebase.models.CommitData;
import com.acidmanic.utility.unirebase.models.SCId;
import java.util.Collection;
import java.util.Collections;

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
import org.tmatesoft.svn.core.wc.SVNWCClient;
import org.tmatesoft.svn.core.wc2.SvnLog;
import org.tmatesoft.svn.core.wc2.SvnOperationFactory;
import org.tmatesoft.svn.core.wc2.SvnRevisionRange;
import org.tmatesoft.svn.core.wc2.SvnTarget;

public class SvnService implements SourceControlService {

    private SVNLogClient logClient;

    private SVNUpdateClient updateClient;

    private File repoFile;

    private SVNURL repoUrl;

    private DefaultSVNRepositoryPool repositoryPool;

    private ISVNAuthenticationManager authenticationManager;

    private SVNClientManager clientManager;
    
    private SVNWCClient wcClient;

    private ISVNOptions svnOptions;
    
    private DefaultSVNOptions sVNOptions;

    private final SvnLogEntryCommitConvertor commitConvertor = new SvnLogEntryCommitConvertor();

    public SvnService(File repoFile) throws Exception {
        this.repoFile = repoFile;

        this.repoUrl = SVNURL.fromFile(repoFile);

        this.repositoryPool = new DefaultSVNRepositoryPool(null, null);

        this.repositoryPool.createRepository(repoUrl, true);
        
        this.svnOptions = new DefaultSVNOptions();

        this.updateClient = new SVNUpdateClient(repositoryPool, svnOptions);

        this.logClient = new SVNLogClient(repositoryPool, svnOptions);
        
        this.wcClient = new SVNWCClient(repositoryPool, svnOptions);

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

    private void clearSVNDirectory(File repoFile) throws SVNException {
        this.wcClient.doCleanup(repoFile);
    }

    @Override
    public ArrayList<CommitData> listAllCommits() throws Exception {

        SvnOperationFactory operationFactory = new SvnOperationFactory();
        SvnLog logOperation = operationFactory.createLog();
        logOperation.setSingleTarget(
                SvnTarget.fromFile(this.repoFile)
        );
        
        logOperation.setRevisionRanges(Collections.singleton(
                SvnRevisionRange.create(
                        SVNRevision.create(1),
                        SVNRevision.HEAD
                )
        ));
        
        Collection<SVNLogEntry> logEntries = logOperation.run(null);


        ArrayList<CommitData> ret = new ArrayList<>();

        logEntries.forEach((entry) -> ret.add(this.commitConvertor.convert(entry)));


        return ret;

    }

    @Override
    public File getRootDirectory() {
        return this.repoFile;
    }

    @Override
    public void dispose() {

        repositoryPool.shutdownConnections(true);
    }

    @Override
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
        System.err.println("TODO: Implement Initialization");
    }

    @Override
    public boolean isRepo() {
        return this.repoFile.toPath().resolve(".svn").toFile().exists();
    }

   
}
