/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.utility.unirebase.migration.commands;

import com.acidmanic.utility.unirebase.migration.MigrationCommand;
import com.acidmanic.utility.unirebase.models.MigrationContext;
import com.acidmanic.utility.unirebase.services.GitService;
import com.acidmanic.utility.unirebase.services.NullSourceControlService;
import com.acidmanic.utility.unirebase.services.Repository;
import com.acidmanic.utility.unirebase.services.SourceControlService;
import com.acidmanic.utility.unirebase.services.SourceControlServiceBuilder;
import com.acidmanic.utility.unirebase.services.SvnService;
import java.util.List;

/**
 *
 * @author 80116
 */
public class AutoSetSourceControlServiceBuilders implements MigrationCommand {

    @Override
    public void execute(MigrationContext context) {
        
        SourceControlServiceBuilder updater = getUpdaterRepoBuilder(context);
        
        SourceControlServiceBuilder committer = makeSCBuilder(context.getConfig().getDestinationRepositoryType());
        
        
        context.setUpdateServiceBuilder(updater);
        
        context.setCommitServiceBuilder(committer);
        
        
    }

    private SourceControlServiceBuilder getUpdaterRepoBuilder(MigrationContext context) {
        
        List<Class<? extends SourceControlService>> repos 
                = Repository.listPresentRepositories(
                context.getSourceRepoLocations().getQueryRootDir());
        
        if(repos.isEmpty()){
            return ((rootDir) -> safe(()->new GitService(rootDir),SourceControlService.NULL));
        }
        
        Class<? extends SourceControlService> scType = repos.get(0);
        
        return makeSCBuilder(scType);
        
    }

    private SourceControlServiceBuilder makeSCBuilder(Class<? extends SourceControlService> scType) {
        if(scType.equals(SvnService.class)){
            return (rootDir) -> safe(() -> new SvnService(rootDir),SourceControlService.NULL);
        }
        
        if(scType.equals(GitService.class)){
            return (rootDir) -> safe(() -> new GitService(rootDir),SourceControlService.NULL);
        }
        
        return SourceControlServiceBuilder.NULL;
    }
    
    
    private interface Func<OUT>{
        OUT run() throws Exception;
    }
    
    private <OUT> OUT safe(Func<OUT> func,OUT defValue){
        try {
            return func.run();
        } catch (Exception e) {
        }
        return defValue;
    }
    
}
