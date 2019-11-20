package com.acidmanic.utility.playgrounds;

import com.acidmanic.utility.svn2git.commitconversion.AuthorsByLoginCommitRefiner;
import com.acidmanic.utility.svn2git.models.MigrationConfig;
import com.acidmanic.utility.svn2git.models.SCId;
import com.acidmanic.utility.svn2git.services.MigrationService;
import com.acidmanic.utility.svn2git.commitmessageformatter.StringCommitMessageFormatter;
public class RepoConvertTest {


    public static void main(String[] args) throws Exception {
        

        String svn = "C:\\Users\\80116\\Documents\\RSB-ELECTRON";
        String git = "C:\\Users\\80116\\Documents\\rsb-electron-git";
        String authorsFile = "C:\\Users\\80116\\Documents\\svn-authors.txt";


        MigrationConfig config = MigrationConfig.Default;

        config.setCommitRefiner( new AuthorsByLoginCommitRefiner(authorsFile));

        config.setCommitMessageFormatter(new StringCommitMessageFormatter("[SVN:{{ID}}] {{MESSAGE}}"));
        

        MigrationService migrationService = new MigrationService(config);

        //TEST For Resumability
        //migrationService.migrate(svn, git, new SCId(SCId.SCID_TYPE_SVN, "3"), "moayedi","Aa12345*");

        migrationService.migrateSvn2Git(svn, git, SCId.createFirst(SCId.SCID_TYPE_SVN), "moayedi","Aa12345*");

        System.out.println("DONE");

    }
}
