package com.acidmanic.utility.svn2git.services;

import java.io.File;

import org.eclipse.jgit.api.Git;


public class GitService {

    private File repoDirectory;

    private File repoGitDirectory;

    private Git git;

    public GitService(File repoDirectory) throws Exception {

        repoDirectory = repoDirectory.toPath().toAbsolutePath().toFile();

        repoDirectory.mkdirs();

        this.repoDirectory = repoDirectory;
        
        this.repoGitDirectory= this.repoDirectory.toPath().resolve(".git").toFile();

        this.git = Git.open(this.repoGitDirectory);

    }

    public GitService(String repoDirectory) throws Exception {
        this(new File(repoDirectory));
    }

    public void addAll() throws Exception {
        addAll(this.git, this.repoGitDirectory);
    }

    private void addAll(Git git, File repo) throws Exception {

        String[] all = repo.list();

        for (String item : all) {
            if (".git".equals(item) || ".svn".equals(item)) {
                System.out.println(item + " has been ignored");
            } else {
                git.add().addFilepattern(item).call();
            }
        }
    }

    public void commit(String message) throws Exception {
        this.git.commit().setMessage(message).call();
    }


    public void init() throws Exception {
        Git.init().setDirectory(this.repoDirectory).call();

        this.git = Git.open(this.repoGitDirectory);
    }
}
