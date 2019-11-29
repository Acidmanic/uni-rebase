package com.acidmanic.utility.svn2git.migration;

import com.acidmanic.utility.svn2git.models.MigrationContext;

public interface MigrationCommand {

    void execute(MigrationContext context);


}