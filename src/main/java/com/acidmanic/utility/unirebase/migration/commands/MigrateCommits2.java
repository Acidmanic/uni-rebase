package com.acidmanic.utility.unirebase.migration.commands;

import com.acidmanic.utility.unirebase.migration.MigrationCommand;
import com.acidmanic.utility.unirebase.migrationstrategies.MigrationStrategy;
import com.acidmanic.utility.unirebase.migration.commands.FigureStartingCommit;

public class MigrateCommits2 extends MigrationStrategy  implements MigrationCommand{

    @Override
    protected void onConfigureSteps(StrategyBuilder builder) {

        builder.first(SetSourceSourceController.class)
                .then(GetAllCommits.class)
                .then(FigureStartingCommit.class)
                .then(SetDestinationSourceController.class);
    }

}