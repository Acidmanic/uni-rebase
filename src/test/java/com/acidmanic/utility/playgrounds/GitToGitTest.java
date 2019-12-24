package com.acidmanic.utility.playgrounds;

import com.acidmanic.utility.Debug;
import com.acidmanic.utility.unirebase.models.MigrationConfig;
import com.acidmanic.utility.unirebase.models.SCId;
import com.acidmanic.utility.unirebase.services.MigrationService;
import com.acidmanic.utility.unirebase.commitmessageformatter.StringCommitMessageFormatter;
import com.acidmanic.utility.unirebase.services.FilesystemService;
import com.acidmanic.utility.unirebase.services.GitService;

public class GitToGitTest {

    public static void main(String[] args) throws Exception {

        FilesystemService fs = new FilesystemService();

        String src = fs.getFile(Debug.DEVELOPE_DIR, "uni-rebase-git").toString();
        String dst = fs.getFile(Debug.DEVELOPE_DIR, "uni-rebase-out").toString();

        MigrationConfig config = MigrationConfig.Default;

        config.setCommitMessageFormatter(new StringCommitMessageFormatter("[SVN:{{ID}}] {{MESSAGE}}"));

        config.setLastCommitedId(SCId.createFirst(SCId.SCID_TYPE_GIT));

        config.setDestinationRepositoryType(GitService.class);

        MigrationService migrationService = new MigrationService(config);

        migrationService.setLogger((text) -> System.out.println(text));

        migrationService.migrateGit2Git(src, dst);

        System.out.println("DONE");

    }

}
