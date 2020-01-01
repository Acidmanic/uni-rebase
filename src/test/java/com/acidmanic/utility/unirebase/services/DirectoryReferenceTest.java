/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.utility.unirebase.services;

import java.io.File;
import java.nio.file.Paths;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author 80116
 */
public class DirectoryReferenceTest {
    
    public DirectoryReferenceTest() {
    }

    @Test
    public void methodShouldReturnBuildDirectory() {
        System.out.println("methodShouldReturnBuildDirectory");
        DirectoryReference instance = new DirectoryReference();
        File expResult = Paths.get("target").resolve("classes").toFile();
        File result = instance.executionDirectory();
        
        assertTrue(new FilesystemService().sameLocation(expResult, result));
        
    }
    
}
