/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.utility.unirebase.migration.commands;

import com.acidmanic.utility.unirebase.migration.MigrationCommand;
import com.acidmanic.utility.unirebase.models.MigrationContext;
import com.acidmanic.utility.unirebase.services.SourceControlService;

/**
 *
 * @author 80116
 */
public class DisposeServices implements MigrationCommand{

    @Override
    public void execute(MigrationContext context) {
        SourceControlService src = context.dataStorage().get(SetSourceSourceController.class);
        
        SourceControlService dst = context.dataStorage().get(SetDestinationSourceController.class);
        
        src.dispose();
        
        dst.dispose();
    }
    
}
