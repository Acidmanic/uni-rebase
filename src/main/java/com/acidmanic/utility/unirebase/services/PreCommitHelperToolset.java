/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.utility.unirebase.services;

/**
 *
 * @author 80116
 */
public class PreCommitHelperToolset {
    
    
    private FilesystemService filesystemHelper = new FilesystemService();

    public FilesystemService getFilesystemHelper() {
        return filesystemHelper;
    }

    public void setFilesystemHelper(FilesystemService filesystemHelper) {
        this.filesystemHelper = filesystemHelper;
    }
    
    
    
}
