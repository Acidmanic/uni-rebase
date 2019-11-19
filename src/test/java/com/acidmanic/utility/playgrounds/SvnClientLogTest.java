package com.acidmanic.utility.playgrounds;


import java.io.File;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNRevisionProperty;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.DefaultSVNRepositoryPool;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNLogClient;
import org.tmatesoft.svn.core.wc.SVNRevision;

public class SvnClientLogTest {



    public static void main(String[] args) throws Exception {
        
        try {


                File repoFile = new File("C:\\Users\\80116\\Desktop\\RSB-BACKEND");

                if(repoFile.exists()){
                    System.out.println("Repository checked and exists.");
                }

                SVNURL url = SVNURL.fromFile(repoFile);

                System.out.println("URL has been made");

                DefaultSVNRepositoryPool repositoryPool = new DefaultSVNRepositoryPool(null, null);

                System.out.println("repositoryPool has been made");

                repositoryPool.createRepository(url, true);

                repositoryPool.setAuthenticationManager(new BasicAuthenticationManager("moayedi", "Aa12345*"));

                SVNLogClient logClient = new SVNLogClient(repositoryPool, new  DefaultSVNOptions());

                System.out.println("client has been made");

                logClient.doLog(new File[]{repoFile}
                    , SVNRevision.BASE, null, null
                    , false, false, 1000000
                    ,(SVNLogEntry logEntry) -> log(logEntry) );

                    System.out.println("Count: " + count);

                    

        } catch ( SVNException e ) {
                System.out.println(e);
        }


        

        
    }


    private static int count =0;

    private static void log(SVNLogEntry logEntry){
        System.out.println("----- " + logEntry.getAuthor() 
        + " - " + logEntry.getRevision() 
        + " - " + logEntry.getDate().toString()
        + " - " + logEntry.getMessage());

        count +=1;
    }
}
