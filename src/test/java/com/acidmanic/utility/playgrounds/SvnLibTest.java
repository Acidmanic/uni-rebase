package com.acidmanic.utility.playgrounds;


import java.io.File;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;

public class SvnLibTest {



    


    public static void main(String[] args) throws Exception {
        
        try {


                File repoFile = new File("C:\\Users\\80116\\Desktop\\rsb-backend-git-clone\\RSB-BACKEND\\RSB-BACKEND");

                if(repoFile.exists()){
                    System.out.println("Repository checked and exists.");
                }

                SVNURL url = SVNURL.fromFile(repoFile);

                System.out.println("URL has been made");

                SVNRepository repository = SVNRepositoryFactory.create( url );

                repository.log(new String[]{}, 1,-1, false, false, (SVNLogEntry logEntry) -> log(logEntry));

                System.out.println("DONE");

        } catch ( SVNException e ) {
                System.out.println(e);
        }
    }



    private static void log(SVNLogEntry logEntry){
        System.out.println("----- " + logEntry.getAuthor() 
        + " - " + logEntry.getRevision() 
        + " - " + logEntry.getDate().toString()
        + " - " + logEntry.getMessage());
    }
}
