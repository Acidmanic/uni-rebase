/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.utility.unirebase.services;

import com.acidmanic.utility.unirebase.models.SCId;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

/**
 *
 * @author 80116
 */
public class MigrationProgress {
    
    
    private final File progressFile;
    
    private SCId lastCommit;
    
    
    public MigrationProgress(File progressFile) {
        this.progressFile = progressFile;
        this.checksetLastCommit();
    }

    private void checksetLastCommit() {
        if(this.progressFile.exists()){
            try {
                String lastId = new String(
                        Files.readAllBytes(this.progressFile.toPath())
                );
                
                this.lastCommit = SCId.create(lastId);
                return;
            } catch (Exception e) {            }

            try {
                this.progressFile.delete();
            } catch (Exception e) {
            }
            this.lastCommit = null;
        }
    }

    public SCId getLastCommit() {
        return lastCommit;
    }

    public void setLastCommit(SCId lastCommit) {
        this.lastCommit = lastCommit;
        
        try {
            if(this.progressFile.exists()){
                this.progressFile.delete();
            }
        } catch (Exception e) {
        }
        
        try {
            Files.write(this.progressFile.toPath()
                    , lastCommit.getId().getBytes()
                    , StandardOpenOption.CREATE);
        } catch (Exception e) {}
    }
    
    
    
    
    
    
}
