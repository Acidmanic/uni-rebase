/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.utility.unirebase.migrationstrategies;

import com.acidmanic.utility.unirebase.migration.commands.AutoSetSourceControlServiceBuilders;
import com.acidmanic.utility.unirebase.migration.commands.CheckForCleanOrResume;
import com.acidmanic.utility.unirebase.migration.commands.MigrateCommits;
import com.acidmanic.utility.unirebase.migration.commands.SetDestinationAsCommitRepo;
import com.acidmanic.utility.unirebase.migration.commands.SetSourceAsUpdateRepo;

/**
 *
 * @author 80116
 */
public class SafeMigrationStrategy extends MigrationStrategy {

    @Override
    protected void onConfigureSteps(StrategyBuilder builder) {

        builder.first(CheckForCleanOrResume.class)
                .then(SetSourceAsUpdateRepo.class)
                .then(SetDestinationAsCommitRepo.class)
                .then(MigrateCommits.class);
    }

}
