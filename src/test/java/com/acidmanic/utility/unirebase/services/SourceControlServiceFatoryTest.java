/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.utility.unirebase.services;

import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author 80116
 */
public class SourceControlServiceFatoryTest {
    
    public SourceControlServiceFatoryTest() {
    }

    @Test
    public void testMakeInstance() {
        System.out.println("makeInstance");
        SourceControlServiceFatory expResult = null;
        SourceControlServiceFatory result = SourceControlServiceFatory.getInstance();
        assertNotEquals(expResult, result);
    }

    @Test
    public void testShouldMakeGitServiceForGitRepo() {
        System.out.println("should find the git repo as a git repository");
        
        File projectRoot = new File("dev-debug").toPath().resolve("git-repo").toFile();
        
        SourceControlService result = SourceControlServiceFatory.getInstance().make(projectRoot);
        
        assertNotNull(result);
        
        assertEquals(GitService.class, result.getClass());
    }
    
    @Test
    public void testShouldMakeSvnServiceForSvnRepo() {
        System.out.println("should find the git repo as a git repository");
        
        File projectRoot = new File("dev-debug").toPath().resolve("svn-repo").toFile();
        
        SourceControlServiceFatory instance = SourceControlServiceFatory.getInstance();
        
        SourceControlService result = instance.make(projectRoot);
        
        assertNotNull(result);
        
        assertEquals(SvnService.class, result.getClass());
    }
    
}
