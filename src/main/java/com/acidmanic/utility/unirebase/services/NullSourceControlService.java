/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.utility.unirebase.services;

import com.acidmanic.utility.unirebase.models.CommitData;
import com.acidmanic.utility.unirebase.models.SCId;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author 80116
 */
public class NullSourceControlService implements SourceControlService{
    
    private File root;

    public NullSourceControlService(File root) {
        this.root = root;
    }
    
    
    @Override
    public void recallProjectState(SCId id) throws Exception {
        System.out.println("Unable to recallProjectState to commit.");
    }

    @Override
    public ArrayList<CommitData> listAllCommits() throws Exception {
        System.out.println("Unable to listAllCommits.");
        return new ArrayList<>();
    }

    @Override
    public File getRootDirectory() {
        return this.root;
    }

    @Override
    public void dispose() {
        
    }

    @Override
    public void ignore(File file) throws Exception {
        System.out.println("Unable to ignore.");
    }

    @Override
    public void acceptAllChanges(String commitMessage) throws Exception {
        System.out.println("Unable to acceptAllChanges.");
    }

    @Override
    public void acceptAllChanges(CommitData commit) throws Exception {
        System.out.println("Unable to acceptAllChanges.");
    }

    @Override
    public void initialize() throws Exception {
        System.out.println("Unable to acceptAllChanges.");
    }

    @Override
    public boolean isRepo() {
        return false;
    }
    
}
