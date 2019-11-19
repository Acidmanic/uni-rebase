package com.acidmanic.utility.playgrounds;


import java.io.File;

import org.tmatesoft.svn.core.SVNDepth;
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
import org.tmatesoft.svn.core.wc.SVNUpdateClient;

public class SvnClientUpdateTest {



    public static void main(String[] args) throws Exception {
        
        try {


                File repoFile = new File("C:\\Users\\80116\\Desktop\\RSB-BACKEND");

                SVNURL url = SVNURL.fromFile(repoFile);

                System.out.println("URL has been made");

                DefaultSVNRepositoryPool repositoryPool = new DefaultSVNRepositoryPool(null, null);

                System.out.println("repositoryPool has been made");

                repositoryPool.createRepository(url, true);

                repositoryPool.setAuthenticationManager(new BasicAuthenticationManager("moayedi", "Aa12345*"));

                SVNUpdateClient updateClient = new SVNUpdateClient(repositoryPool, new  DefaultSVNOptions());

                System.out.println("client has been made");

                updateToRevision(repoFile, updateClient, 1);
                logDirectory(repoFile);

            
                updateToRevision(repoFile, updateClient, 4);
                logDirectory(repoFile);

                updateToRevision(repoFile, updateClient, SVNRevision.HEAD.getNumber());
                logDirectory(repoFile);
            

        } catch (SVNException e) {
            System.out.println(e);
        }

    }

    private static void updateToRevision(File repoFile, SVNUpdateClient updateClient, long revision) throws SVNException {
        clearSVNDirectory(repoFile);

        updateClient.doUpdate(repoFile, SVNRevision.create(revision), SVNDepth.INFINITY, true, true);

        System.out.println("Updated to "+revision+"'th' revision");
    }


    private static void logDirectory(File repoFile) {

        File trunk = repoFile.toPath().resolve("trunk").toFile();

        if(trunk.exists()){
            String[] subs = trunk.list();

            for(String sub:subs){
                System.out.println(">> " + sub);
            }
        }
    }

    private static void clearSVNDirectory(File repoFile) {
    }

    private static int count = 0;

    private static void log(SVNLogEntry logEntry){
        System.out.println("----- " + logEntry.getAuthor() 
        + " - " + logEntry.getRevision() 
        + " - " + logEntry.getDate().toString()
        + " - " + logEntry.getMessage());

        count +=1;
    }
}
