package com.acidmanic.utility.playgrounds;

import com.acidmanic.utility.svn2git.commitconversion.AuthorsByLoginCommitRefiner;
import com.acidmanic.utility.svn2git.models.MigrationConfig;
import com.acidmanic.utility.svn2git.services.AuthorsListFile;
import com.acidmanic.utility.svn2git.services.MigrationService;

public class RepoConvertTest {


    public static void main(String[] args) throws Exception {
        

        String svn = "C:\\Users\\80116\\Documents\\RSB-BACKEND";
        String git = "C:\\Users\\80116\\Documents\\rsb-backend-git";
        String authorsFile = "C:\\Users\\80116\\Documents\\svn-authors.txt";


        MigrationConfig config = MigrationConfig.Default;

        config.setCommitRefiner( new AuthorsByLoginCommitRefiner(authorsFile));

        MigrationService migrationService = new MigrationService(config);

        migrationService.migrate(svn, git,"moayedi","Aa12345*");

        System.out.println("DONE");

    }
}
