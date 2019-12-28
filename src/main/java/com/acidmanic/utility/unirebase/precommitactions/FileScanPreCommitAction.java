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
import java.util.function.Consumer;

/**
 *
 * @author 80116
 */
public abstract class FileScanPreCommitAction implements PreCommitAction{
    
    
    protected PreCommitHelperToolset helpers;
    protected Consumer<String> logger;
    protected CommitMessageFormatter formatter;
    

    @Override
    public void onPreCommit(PreCommitHelperToolset helpers, File sourceDirectory, Consumer<String> logger, CommitData commit, CommitMessageFormatter formatter) {
        this.helpers = helpers;
        this.logger = logger;
        this.formatter = formatter;
        
        
        checkFile(sourceDirectory, commit);
    }
    
    
    private void checkFile( File dir, CommitData commit){
        
        checkFileBeforeBeingCommited(dir, commit);
        
        File[] files = dir.listFiles();
        
        for(File f : files){
            
            if(f.isDirectory()){
                checkFile(dir, commit);
            }
        }
    }
    
    
    protected abstract void checkFileBeforeBeingCommited(File file,CommitData commit);
    
    
}
