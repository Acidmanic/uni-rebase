/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.utility.unirebase.precommitactions;

import com.acidmanic.utility.unirebase.models.CommitData;
import com.acidmanic.utility.unirebase.services.SafeRunner;
import java.io.File;

/**
 *
 * @author 80116
 */
public class DeleteFileByName extends FileScanPreCommitAction{

    
    private final String regex;

    public DeleteFileByName(String regex) {
        this.regex = regex;
    }
    
    
    @Override
    protected void checkFileBeforeBeingCommited(File file, CommitData commit) {
    
            if(file.isDirectory() && file.getName().matches(this.regex)){
                new SafeRunner().log(this.logger).describe("Error Deleting File " + file.getAbsolutePath())
                        .run(()->file.delete());
            }
    }
    
}
