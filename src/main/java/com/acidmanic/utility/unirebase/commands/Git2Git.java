/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.utility.unirebase.commands;

import com.acidmanic.commandline.commands.CommandBase;
import com.acidmanic.commandline.commands.parameters.ParameterBuilder;
import com.acidmanic.utility.unirebase.commitmessageformatter.CommitMessageFormatter;
import com.acidmanic.utility.unirebase.commitmessageformatter.DefaultCommitMessageFormatter;
import com.acidmanic.utility.unirebase.commitmessageformatter.StringCommitMessageFormatter;
import com.acidmanic.utility.unirebase.models.MigrationConfig;
import com.acidmanic.utility.unirebase.models.SCId;
import com.acidmanic.utility.unirebase.plugins.UniRebasePluginProfile;
import com.acidmanic.utility.unirebase.precommitactions.BatchPreCommitAction;
import com.acidmanic.utility.unirebase.precommitactions.Log;
import com.acidmanic.utility.unirebase.precommitactions.PreCommitAction;
import com.acidmanic.utility.unirebase.services.GitService;
import com.acidmanic.utility.unirebase.services.MigrationService;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 80116
 */
public class Git2Git extends CommandBase {

    @Override
    protected String getUsageString() {
        return "This command will copy a git project to new project allowing for commit modifications";
    }

    @Override
    public void execute() {
        if (!argumentCheck(3)) {

            prepareArguments();

            MigrationConfig config = MigrationConfig.Default;

            config.setCommitMessageFormatter(this.formatter);

            if (isParameterProvided("clean")) {
                config.setForceClean(getParameterValue("clean"));
            }

            config.setLastCommitedId(SCId.createFirst(SCId.SCID_TYPE_GIT));

            config.setDestinationRepositoryType(GitService.class);
            
            config.setPreCommitAction(getPreCommitAction());

            try {
                performMigration(config);
            } catch (Exception e) {
            }

            this.info("DONE");
        }
    }

    @Override
    protected void defineParameters(ParameterBuilder builder) {

        builder.named("src-repo").described("the root directory of source project.")
                .ofType(File.class).mandatory().indexAt(0)
                .newParam()
                .named("dst-dir").described("the root directory of destination project.")
                .ofType(File.class).mandatory().indexAt(1)
                .newParam().named("commit-pattern").described("This will be used to format commit messages while moving."
                + "\nyou can use these tags: "
                + "\n{{MESSAGE}}: will be replaced by the commit message."
                + "\n{{ID}}: will be replaced by SVN revision ID ."
                + "\n{{DATE}}: will be replaced by commit date ."
                + "\n{{AUTHOR}}: will be replaced by author name ."
                + "\nEx.: the format '[{{ID}}] - {{MESSAGE}}' will create git commits like "
                + "\n'[10022] - fix memoty leak cause in main activity'.").ofType(String.class).optional()
                .ofType(String.class).mandatory().indexAt(2)
                .newParam().named("clean").described("clean=true will remove any work from last run, and clean=false makes app to resume last work.")
                .ofType(boolean.class).optional()
                .newParam().named("--precommit").described(getPreCommitDescription()).ofType(String.class)
                .optional();
    }

    private File src;
    private File dst;
    private CommitMessageFormatter formatter;

    private void prepareArguments() {
        src = getParameterValue("svn-repo");

        dst = getParameterValue("git-destination-dir");

        this.formatter = new DefaultCommitMessageFormatter();
        if (this.isParameterProvided("commit-pattern")) {
            String pattern = this.getParameterValue("commit-pattern");
            this.formatter = new StringCommitMessageFormatter(pattern);
        }

    }

    private void performMigration(MigrationConfig config) throws Exception {
        MigrationService migrationService = new MigrationService(config);

        migrationService.setLogger((text) -> info(text));

        String svnPath = src.getAbsolutePath();
        String gitPath = dst.getAbsolutePath();

        migrationService.migrateSvn2Git(svnPath, gitPath);

    }

    private String getPreCommitDescription() {
        return "You can implement the Interface: 'PreCommitAction' in a java "
                + "library project and drop your jar file into 'plugins' directory"
                + ", then passing the name of your implementation class to this "
                + "parameter, will execute your implementation right before each "
                + " commit.";
    }

    private PreCommitAction getPreCommitAction() {
        BatchPreCommitAction preCommitAction = new BatchPreCommitAction();
        
        preCommitAction.add(new Log());
        
        String actionName = getParameterValue("--precommit");
        
        PreCommitAction action = null;
        
        try {
            action = UniRebasePluginProfile.makeInstance().makeObject(actionName);
        } catch (Exception ex) {
            Logger.getLogger(Git2Git.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(action!=null){
            preCommitAction.add(action);
        }
        
        return preCommitAction;
    }

}
