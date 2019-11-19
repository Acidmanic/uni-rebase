package com.acidmanic.utility.playgrounds;

import com.acidmanic.utility.svn2git.services.AuthorsListFile;
import com.acidmanic.utility.svn2git.services.MigrationService;

public class RepoConvertTest {


    public static void main(String[] args) throws Exception {
        

        String svn = "C:\\Users\\80116\\Documents\\RSB-ELECTRON";
        String git = "C:\\Users\\80116\\Documents\\rsb-elec-git";
        String authorsFile = "C:\\Users\\80116\\Documents\\svn-authors.txt";


        AuthorsListFile authors = AuthorsListFile.load(authorsFile);

        MigrationService migrationService = new MigrationService();

        migrationService.migrate(svn, git,"moayedi","Aa12345*");

        System.out.println("DONE");

    }
}
