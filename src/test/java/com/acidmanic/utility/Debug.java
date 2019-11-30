/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.utility;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author diego
 */
public class Debug {
    
    
    public static final String DEVELOPE_DIR = "dev-debug";
    
    public static String read(String debugDataFile){
        try {
            byte[] data = Files.readAllBytes(Paths.get(".").resolve(DEVELOPE_DIR).resolve(debugDataFile));
            return new String(data);
        } catch (Exception e) {
        }
        
        return "";
    }
}
