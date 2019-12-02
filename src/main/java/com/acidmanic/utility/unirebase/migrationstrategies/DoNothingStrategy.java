/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.utility.unirebase.migrationstrategies;

import com.acidmanic.utility.unirebase.migration.commands.DoingNothing;

/**
 *
 * @author 80116
 */
public class DoNothingStrategy extends MigrationStrategy{

    @Override
    protected void onConfigureSteps(StrategyBuilder builder) {
        
        builder.first(DoingNothing.class);
    }
    
}
