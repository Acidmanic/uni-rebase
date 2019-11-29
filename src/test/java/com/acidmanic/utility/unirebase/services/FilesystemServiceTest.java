package com.acidmanic.utility.unirebase.services;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class FilesystemServiceTest{




    @Test
    public void shouldRecognizeUnNormalDottedPathsAreTheSame(){


        FilesystemService sut = new FilesystemService();

        File f1 = new File("subDirectory").toPath().resolve("..").toFile();
        File f2 = new File(".");
        
        boolean result = sut.sameLocation(f1,f2);

        Assert.assertTrue(result);
    }
}