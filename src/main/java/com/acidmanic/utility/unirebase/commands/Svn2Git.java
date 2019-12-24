package com.acidmanic.utility.unirebase.commands;

import java.io.File;

import com.acidmanic.commandline.commands.CommandBase;
import com.acidmanic.commandline.commands.parameters.ParameterBuilder;
import com.acidmanic.utility.unirebase.commitconversion.AuthorsByLoginCommitRefiner;
import com.acidmanic.utility.unirebase.commitmessageformatter.CommitMessageFormatter;
import com.acidmanic.utility.unirebase.commitmessageformatter.DefaultCommitMessageFormatter;
import com.acidmanic.utility.unirebase.commitmessageformatter.StringCommitMessageFormatter;
import com.acidmanic.utility.unirebase.models.MigrationConfig;
import com.acidmanic.utility.unirebase.models.SCId;
import com.acidmanic.utility.unirebase.services.MigrationService;


public class Svn2Git extends CommandBase {

    private File svn ;
    private File git ;
    private File authorsFile ;
    private CommitMessageFormatter formatter;

    private void prepareArguments(){
        svn = getParameterValue("svn-repo");

        git = getParameterValue("git-destination-dir");
        
        authorsFile = getParameterValue("authors-file");

        this.formatter = new DefaultCommitMessageFormatter();
        if (this.isParameterProvided("commit-pattern")){
            String pattern = this.getParameterValue("commit-pattern");
            this.formatter = new StringCommitMessageFormatter(pattern);
        }

        
    }

    @Override
    protected void defineParameters(ParameterBuilder builder) {

        builder.named("svn-repo").described("the root directory of svn project.")
               .ofType(File.class).mandatory().indexAt(0)
        .newParam()
               .named("git-destination-dir").described("the root directory of git project.")
               .ofType(File.class).mandatory().indexAt(1)
        .newParam()
               .named("authors-file").described("This is a text file containing names and emails of commiters. "+
               "each line introduces one person in this format: (login-name) = (author-name) <(author-email)>," +
               "(ex.: Mani = Mani Moayedi <acidmanic.moayedi@gmail.com>)")
               .ofType(File.class).mandatory().indexAt(2)
        .newParam().named("user-name").described("SVN Repository's username").ofType(String.class).optional()
        .newParam().named("password").described("SVN Repository's password").ofType(String.class).optional()
        .newParam().named("commit-pattern").described("This will be used to format commit messages while moving."+
        "\nyou can use these tags: "+
        "\n{{MESSAGE}}: will be replaced by the commit message."+
        "\n{{ID}}: will be replaced by SVN revision ID ."+
        "\n{{DATE}}: will be replaced by commit date ."+
        "\n{{AUTHOR}}: will be replaced by author name ."+
        "\nEx.: the format '[{{ID}}] - {{MESSAGE}}' will create git commits like "+
        "\n'[10022] - fix memoty leak cause in main activity'.").ofType(String.class).optional()
        .newParam().named("src-dir").described("by default it's 'trunk', but is some cases it can be other directories. this argument will set source directory when it's something other thatn trunk.")
                .ofType(String.class).optional()
        .newParam().named("clean").described("clean=true will remove any work from last run, and clean=false makes app to resume last work.")
                .ofType(boolean.class).optional();
        

    }


    @Override
    public void execute() {
        if(!argumentCheck(3)){


            prepareArguments();

            MigrationConfig config = MigrationConfig.Default;

            config.setCommitRefiner( new AuthorsByLoginCommitRefiner(this.authorsFile.getAbsolutePath()));

            config.setCommitMessageFormatter(this.formatter);
            
            if(isParameterProvided("user-name")) config.setUsername(getParameterValue("user-name"));

            if(isParameterProvided("password")) config.setPassword(getParameterValue("password"));

            if(isParameterProvided("clean")) config.setForceClean(getParameterValue("clean"));
            
            config.setLastCommitedId(SCId.createFirst(SCId.SCID_TYPE_SVN));

           try {
                performMigration(config);
           } catch (Exception e) {
           }

            this.info("DONE");
        }
    }

    private void performMigration(MigrationConfig config)  throws Exception{
        MigrationService migrationService = new MigrationService(config);
        
        migrationService.setLogger((text) -> info(text));

        String svnPath = svn.getAbsolutePath();
        String gitPath = git.getAbsolutePath();

        migrationService.migrateSvn2Git(svnPath, gitPath);
   

    }

    @Override
    protected String getUsageString() {
        return "This command convert a svn project to a git project in given directory name, then transfers each svn-commit to a git-commit.";
    }



}