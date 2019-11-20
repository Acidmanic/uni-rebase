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
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;

public class GitLogTest {

    public static void main(String[] args) throws Exception {

        File repoFile = new File(".").toPath().toAbsolutePath().toFile();


        File repoRoot = repoFile;

        repoFile = repoRoot.toPath().resolve(".git").toFile();

        Git git = Git.open(repoFile);

        status(git);

       git.log().all().call().forEach((rev) -> {
           
           System.out.println(rev.getFullMessage());

           PersonIdent ident = rev.getAuthorIdent();
           
           System.out.println(ident.getEmailAddress());

           System.out.println(ident.getName());

           System.out.println(ident.getWhen().toString());

           System.out.println("EX:"+ident.toExternalString());

           System.out.println(ObjectId.toString(rev.getId()));
           
           System.out.println("------------------------------------------");
       });

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
