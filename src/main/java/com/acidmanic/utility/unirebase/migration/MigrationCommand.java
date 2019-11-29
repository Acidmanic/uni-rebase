package com.acidmanic.utility.unirebase.migration;

import com.acidmanic.utility.unirebase.models.MigrationContext;

public interface MigrationCommand {

    void execute(MigrationContext context);


}