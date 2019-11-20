package com.acidmanic.utility.svn2git.migrationstrategies;

import com.acidmanic.utility.svn2git.models.CommitData;
import com.acidmanic.utility.svn2git.services.SourceControlService;

public interface MigrationStrategy {




    void prepare(SourceControlService sourceRepo,SourceControlService destinationRepo);

    void deliverCommit(CommitData commit);

    void cleanUp();
}