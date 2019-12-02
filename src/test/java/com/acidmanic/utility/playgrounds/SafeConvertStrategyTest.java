package com.acidmanic.utility.playgrounds;

import com.acidmanic.utility.Debug;
import com.acidmanic.utility.unirebase.commitconversion.AuthorsByLoginCommitRefiner;
import com.acidmanic.utility.unirebase.models.MigrationConfig;
import com.acidmanic.utility.unirebase.models.SCId;
import com.acidmanic.utility.unirebase.services.MigrationService;
import com.acidmanic.utility.unirebase.commitmessageformatter.StringCommitMessageFormatter;
import com.acidmanic.utility.unirebase.migrationstrategies.SafeMigrationStrategy;
import com.acidmanic.utility.unirebase.services.FilesystemService;
import com.acidmanic.utility.unirebase.services.SvnService;
public class SafeConvertStrategyTest {


    public static void main(String[] args) throws Exception {
        
        FilesystemService fs = new FilesystemService();

        String svn =  fs.getFile(Debug.DEVELOPE_DIR,"uni-rebase-test").toString();
        String git =  fs.getFile(Debug.DEVELOPE_DIR,"uni-rebase-test-git").toString();
        String authorsFile = fs.getFile(Debug.DEVELOPE_DIR,"svn-authors.txt").toString();


        MigrationConfig config = MigrationConfig.Default;

        config.setCommitRefiner( new AuthorsByLoginCommitRefiner(authorsFile));

        config.setCommitMessageFormatter(new StringCommitMessageFormatter("[SVN:{{ID}}] {{MESSAGE}}"));
        
        config.setLastCommitedId(SCId.createFirst(SCId.SCID_TYPE_SVN));
        
        config.setDestinationRepositoryType(SvnService.class);

        config.setUsername(Debug.read("username"));

        config.setPassword(Debug.read("password"));
        
        config.setMigrationStrategy(new SafeMigrationStrategy());

        MigrationService migrationService = new MigrationService(config);

        migrationService.setLogger((text)-> System.out.println(text));

        migrationService.migrateSvn2Git(svn, git);

        System.out.println("DONE");

    }

}
