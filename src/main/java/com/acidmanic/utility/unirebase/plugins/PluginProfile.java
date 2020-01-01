/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.utility.unirebase.plugins;

import java.util.List;

/**
 *
 * @author 80116
 */
public interface PluginProfile {

    List<Class> allClasses();

    Class byFullName(String name) throws ClassNotFoundException;

    /**
     * *
     *
     * This method first looks for full name match, if it doesn't find any, it
     * will search for simple name match. If you use this method, there is a
     * chance that the result is different from your expectation. for example
     * when there are two classes which one's full name (default package) is
     * match with other's simple name.
     *
     * @param name
     * @return
     */
    Class byName(String name) throws ClassNotFoundException;

    Class bySimpleName(String name) throws ClassNotFoundException;

    void loadPlugins();

    <T> T makeObject(String className) throws Exception;

    <T> T makeObject(String className, Object... arguments) throws Exception;
    
}
