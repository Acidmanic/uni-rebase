package com.acidmanic.utility.svn2git.services;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class FilesystemServiceTest{




    @Test
    public void shouldRecognizeUnNormalDottedPathsAreTheSame(){


        FilesystemService sut = new FilesystemService();

        File f1 = new File("subDirectory\\..");
        File f2 = new File(".");
        
        boolean result = sut.sameLocation(f1,f2);

        Assert.assertTrue(result);
    }
}