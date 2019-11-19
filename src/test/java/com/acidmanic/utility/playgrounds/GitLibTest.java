package com.acidmanic.utility.playgrounds;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.lib.Repository;

public class GitLibTest {

    public static void main(String[] args) throws Exception {

        File repoFile = new File("..\\output.git").toPath().toAbsolutePath().toFile();

        if (!repoFile.exists()) {
            repoFile.mkdirs();
        }

        // Git.init().setDirectory(repoFile).call();

        File repoRoot = repoFile;

        repoFile = repoRoot.toPath().resolve(".git").toFile();

        System.out.println("Repository created");

        File tempFile = repoRoot.toPath().resolve("temp" + new Date().getTime()).toFile();

        tempFile.createNewFile();

        System.out.println("Temp file put");

        Git git = Git.open(repoFile);

        addAll(git, repoRoot);

        System.out.println("Temp File added ");

        status(git);

        git.commit().setMessage("Temp File Added").call();

        System.out.println("TempFile Commited");

    }

    private static void status(Git git) throws GitAPIException {
        Status status = git.status().call();

        System.out.println("GREENNNNN ADDDDD ----------");
        for (String line : status.getAdded())
            System.out.println(line);
        System.out.println("REDDD MODIFIED ----------");
        for (String line : status.getChanged())
            System.out.println(line);
        System.out.println("REDDD UN TRACK ED ----------");
        for (String line : status.getUntracked())
            System.out.println(line);
    }

    private static void addAll(Git git, File repo) throws Exception {


        String[] all = repo.list();

        for(String item:all){
            if(".git".equals(item)||".svn".equals(item)){
                System.out.println(item + " has been ignored");
            }else{
                git.add().addFilepattern(item).call();
            }
        }
    }
}
