/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.utility.unirebase.services;

import java.io.File;

/**
 *
 * @author 80116
 */
public class DirectoryReference {

    public File executionDirectory() {
        try {
            return new File(this.getClass().getProtectionDomain()
                    .getCodeSource().getLocation()
                    .toURI()).getAbsoluteFile();
        } catch (Exception ex) {

        }

        return new File(".");
    }
}
