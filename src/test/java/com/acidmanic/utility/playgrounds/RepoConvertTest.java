package com.acidmanic.utility.playgrounds;

import com.acidmanic.utility.Debug;
import com.acidmanic.utility.unirebase.commitconversion.AuthorsByLoginCommitRefiner;
import com.acidmanic.utility.unirebase.models.MigrationConfig;
import com.acidmanic.utility.unirebase.models.SCId;
import com.acidmanic.utility.unirebase.services.MigrationService;
import com.acidmanic.utility.unirebase.commitmessageformatter.StringCommitMessageFormatter;
import com.acidmanic.utility.unirebase.services.FilesystemService;
public class RepoConvertTest {


    public static void main(String[] args) throws Exception {
        
        FilesystemService fs = new FilesystemService();

        String svn =  fs.getFile(Debug.DEVELOPE_DIR,"uni-rebase-test").toString();
        String git =  fs.getFile(Debug.DEVELOPE_DIR,"uni-rebase-test-git").toString();
        String authorsFile = fs.getFile(Debug.DEVELOPE_DIR,"svn-authors.txt").toString();


        MigrationConfig config = MigrationConfig.Default;

        config.setCommitRefiner( new AuthorsByLoginCommitRefiner(authorsFile));

        config.setCommitMessageFormatter(new StringCommitMessageFormatter("[SVN:{{ID}}] {{MESSAGE}}"));
        
//        config.setSourcesDirectory(".");

        config.setLastCommitedId(SCId.createFirst(SCId.SCID_TYPE_SVN));

        config.setUsername(Debug.read("username"));

        config.setPassword(Debug.read("password"));

        MigrationService migrationService = new MigrationService(config);

        migrationService.setLogger((text)-> System.out.println(text));

        //TEST For Resumability
        //migrationService.migrate(svn, git, new SCId(SCId.SCID_TYPE_SVN, "3"), "moayedi","Aa12345*");

        migrationService.migrateSvn2Git(svn, git);

        System.out.println("DONE");

    }

}
