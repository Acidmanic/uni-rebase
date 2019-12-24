package com.acidmanic.utility.unirebase.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import com.acidmanic.utility.unirebase.models.CommitData;
import com.acidmanic.utility.unirebase.models.SCId;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.revwalk.RevCommit;

public class GitService implements SourceControlService {

    private File repoDirectory;

    private File repoGitDirectory;

    private Git git;

    public GitService(File repoDirectory) throws Exception {

        repoDirectory = repoDirectory.toPath().toAbsolutePath().toFile();

        repoDirectory.mkdirs();

        this.repoDirectory = repoDirectory;

        this.repoGitDirectory = this.repoDirectory.toPath().resolve(".git").toFile();

        if (this.repoGitDirectory.exists()) {
            this.git = Git.open(this.repoGitDirectory);
        }

    }

    public GitService(String repoDirectory) throws Exception {
        this(new File(repoDirectory));
    }

    private void addAll(Git git, File repo) throws Exception {

        Status status = git.status().call();

        List<String> added = new ArrayList<>();
        List<String> removed = new ArrayList<>();

        added.addAll(status.getUntracked());

        removed.addAll(status.getMissing());

        removed.addAll(status.getRemoved());

        added.addAll(status.getModified());

        GitIgnoreFile gitIgnoreFile = new GitIgnoreFile(repo);

        for (String item : added) {
            if (".git".equals(item) || gitIgnoreFile.isIgnored(item)) {
                System.out.println(item + " has been ignored");
            } else {
                git.add().addFilepattern(item).call();
            }
        }

        for (String item : removed) {
            if (gitIgnoreFile.isIgnored(item)) {
                System.out.println(item + " has been ignored");
            } else {
                git.rm().addFilepattern(item).call();
            }
        }

    }

    public void commit(String message) throws Exception {
        this.git.commit().setMessage(message).call();
    }

    public void commit(CommitData commit) throws Exception {

        PersonIdent committer = new PersonIdent(commit.getAuthorName(), commit.getAuthorEmail(), commit.getDate(),
                TimeZone.getDefault());

        this.git.commit().setMessage(commit.getMessage()).setCommitter(committer).setAuthor(committer).call();
    }

    @Override
    public File getRootDirectory() {
        return this.repoDirectory;
    }

    @Override
    public boolean isRepo() {
        try {
            if (this.repoGitDirectory.exists()) {
                this.git.status().call();
                return true;
            }

        } catch (Exception e) {
        }

        return false;
    }

    @Override
    public void recallProjectState(SCId id) throws Exception {
        FilesystemService fs = new FilesystemService();

        fs.deleteContent(repoDirectory, new String[]{".git"});

        this.git.checkout().addPath(".").setAllPaths(true).call();

        this.git.checkout().setName(id.getGitHash()).call();
                
        fs.deleteContent(repoDirectory, new String[]{".git"});

        this.git.checkout().addPath(".").setAllPaths(true).call();
        
    }

    @Override
    public ArrayList<CommitData> listAllCommits() throws Exception {

        ArrayList<CommitData> ret = new ArrayList<>();

        Iterable<RevCommit> commits = this.git.log().call();

        commits.forEach(rev -> ret.add(convert(rev)));

        return ret;
    }

    private CommitData convert(RevCommit rev) {

        PersonIdent author = rev.getAuthorIdent();

        CommitData ret = new CommitData();

        ret.setAuthorEmail(author.getEmailAddress());

        ret.setAuthorName(author.getName());

        ret.setDate(author.getWhen());

        ret.setIdentifier(new SCId(SCId.SCID_TYPE_GIT, rev.getId().name()));

        ret.setMessage(rev.getFullMessage());

        return ret;
    }

    @Override
    public void dispose() {
        this.git.gc();

        this.git.close();
    }

    @Override
    public void ignore(File file) throws Exception {
        try {
            this.git.rm().addFilepattern(file.getPath()).call();
        } catch (Exception e) {
        }

        GitIgnoreFile ignore = new GitIgnoreFile(this.repoDirectory);

        ignore.ignore(file);

    }

    @Override
    public void acceptAllChanges(String commitMessage) throws Exception {

        this.addAll(this.git, this.repoDirectory);

        this.commit(commitMessage);
    }

    @Override
    public void acceptAllChanges(CommitData commit) throws Exception {

        this.addAll(this.git, this.repoDirectory);

        this.commit(commit);
    }

    @Override
    public void initialize() throws Exception {
//        this.git = Git.init().setDirectory(this.repoDirectory).call();

        Git.init().setDirectory(this.repoDirectory).call();

        this.git = Git.open(this.repoGitDirectory);

    }
}
