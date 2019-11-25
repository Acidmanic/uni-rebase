package com.acidmanic.utility.svn2git.commands;

import com.acidmanic.commandline.commands.CommandBase;
import com.acidmanic.commandline.commands.parameters.ParameterBuilder;
import com.acidmanic.utility.svn2git.commitconversion.AuthorsByLoginCommitRefiner;
import com.acidmanic.utility.svn2git.commitmessageformatter.CommitMessageFormatter;
import com.acidmanic.utility.svn2git.commitmessageformatter.DefaultCommitMessageFormatter;
import com.acidmanic.utility.svn2git.commitmessageformatter.StringCommitMessageFormatter;
import com.acidmanic.utility.svn2git.models.MigrationConfig;
import com.acidmanic.utility.svn2git.models.SCId;
import com.acidmanic.utility.svn2git.services.MigrationService;


public class Svn2Git extends CommandBase {

    private String svn ;
    private String git ;
    private String authorsFile ;
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
               .ofType(String.class).mandatory().indexAt(0)
        .newParam()
               .named("git-destination-dir").described("the root directory of svn project.")
               .ofType(String.class).mandatory().indexAt(1)
        .newParam()
               .named("authors-file").described("This is a text file containing names and emails of commiters. "+
               "each line introduces one person in this format: (login-name) = (author-name) <(author-email)>," +
               "(ex.: Mani = Mani Moayedi <acidmanic.moayedi@gmail.com>)")
               .ofType(String.class).mandatory().indexAt(2)
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
        ;

    }


    @Override
    public void execute() {
        if(!argumentCheck(3)){


            prepareArguments();

            MigrationConfig config = MigrationConfig.Default;

            config.setCommitRefiner( new AuthorsByLoginCommitRefiner(this.authorsFile));

            config.setCommitMessageFormatter(this.formatter);
            

           try {
                performMigration(config);
           } catch (Exception e) {
           }
            

           warning(getParameterValue("svn-repo"));

        warning(getParameterValue("git-destination-dir"));

        warning(getParameterValue("authors-file"));

        warning(getParameterValue("commit-pattern"));

        warning(getParameterValue("user-name"));

        warning(getParameterValue("password"));


            this.info("DONE");
        }
    }

    private void performMigration(MigrationConfig config)  throws Exception{
        MigrationService migrationService = new MigrationService(config);

        if(isParameterProvided("user-name") && isParameterProvided("password")){
            migrationService.migrateSvn2Git(svn, git, SCId.createFirst(SCId.SCID_TYPE_SVN), 
            this.getParameterValue("user-name"),this.getParameterValue("password"));
        }else{
            migrationService.migrateSvn2Git(svn, git, SCId.createFirst(SCId.SCID_TYPE_SVN));
        }

        

        
    }

    @Override
    protected String getUsageString() {
        return "This command convert a svn project to a git project in given directory name, then transfers each svn-commit to a git-commit.";
    }



}