/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.utility.unirebase.precommitactions;

import com.acidmanic.utility.unirebase.commitmessageformatter.CommitMessageFormatter;
import com.acidmanic.utility.unirebase.models.CommitData;
import com.acidmanic.utility.unirebase.services.PreCommitHelperToolset;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 
 * @author 80116
 */
public class BatchPreCommitAction implements PreCommitAction{ 

    
    private final List<PreCommitAction> actions = new ArrayList<>();
    
    
    public BatchPreCommitAction add(PreCommitAction action){
        
        this.actions.add(action);
        
        return this;
    }
    
    @Override
    public void onPreCommit(PreCommitHelperToolset helpers, File sourceDirectory, Consumer<String> logger, CommitData commit, CommitMessageFormatter formatter) {
        this.actions.forEach( action -> action.onPreCommit(helpers, sourceDirectory, logger, commit, formatter));
    }
    
}
