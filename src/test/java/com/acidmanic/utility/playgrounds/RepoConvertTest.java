package com.acidmanic.utility.playgrounds;

import com.acidmanic.utility.unirebase.commitconversion.AuthorsByLoginCommitRefiner;
import com.acidmanic.utility.unirebase.models.MigrationConfig;
import com.acidmanic.utility.unirebase.models.SCId;
import com.acidmanic.utility.unirebase.services.MigrationService;
import com.acidmanic.utility.unirebase.commitmessageformatter.StringCommitMessageFormatter;
public class RepoConvertTest {


    public static void main(String[] args) throws Exception {
        

        String svn = "C:\\Users\\80116\\Documents\\GW-BACKEND";
        String git = "C:\\Users\\80116\\Documents\\gw-backend-git";
        String authorsFile = "C:\\Users\\80116\\Documents\\svn-authors.txt";


        MigrationConfig config = MigrationConfig.Default;

        config.setCommitRefiner( new AuthorsByLoginCommitRefiner(authorsFile));

        config.setCommitMessageFormatter(new StringCommitMessageFormatter("[SVN:{{ID}}] {{MESSAGE}}"));
        
        config.setSourcesDirectory(".");

        config.setLastCommitedId(SCId.createFirst(SCId.SCID_TYPE_SVN));

        config.setUsername("moayedi");

        config.setPassword("Aa12345*");

        MigrationService migrationService = new MigrationService(config);

        migrationService.setLogger((text)-> System.out.println(text));

        //TEST For Resumability
        //migrationService.migrate(svn, git, new SCId(SCId.SCID_TYPE_SVN, "3"), "moayedi","Aa12345*");

        migrationService.migrateSvn2Git(svn, git);

        System.out.println("DONE");

    }
}
