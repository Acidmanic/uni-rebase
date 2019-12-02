/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.utility.unirebase.migration.commands;

import com.acidmanic.utility.unirebase.migration.MigrationCommand;
import com.acidmanic.utility.unirebase.models.MigrationContext;
import com.acidmanic.utility.unirebase.models.RepositoryLocations;
import com.acidmanic.utility.unirebase.services.Repository;
import com.acidmanic.utility.unirebase.services.SvnService;

/**
 *
 * @author 80116
 */
public class SetDestinationAsCommitRepo implements MigrationCommand{

    @Override
    public void execute(MigrationContext context) {
        context.setCommitRepoLocations(
                getCommitRepoLocations(context)
        );
    }

    private RepositoryLocations getCommitRepoLocations(MigrationContext context) {
        
        if(context.getConfig().getDestinationRepositoryType().equals(SvnService.class)){
            return Repository.svnRepositoryLocations(context.getDestinationDirectory().getAbsolutePath());
        }
        
        return Repository.gitRepositoryLocations(context.getDestinationDirectory().getAbsolutePath());
    }
    
}
