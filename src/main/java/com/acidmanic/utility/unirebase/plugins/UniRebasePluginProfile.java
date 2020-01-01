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
public class UniRebasePluginProfile implements PluginProfile{

    
    private static UniRebasePluginProfile instance = null;
    
    private final BasicPluginProfile pluginProfile;
    
    private UniRebasePluginProfile(){
        this.pluginProfile = new BasicPluginProfile("plugins");
    }
    
    public static synchronized UniRebasePluginProfile makeInstance(){
        if(instance == null){
            instance = new UniRebasePluginProfile();
        }
        return instance;
    }
    
    
    @Override
    public List<Class> allClasses() {
        return this.pluginProfile.allClasses();
    }

    @Override
    public Class byFullName(String name) throws ClassNotFoundException {
        return this.pluginProfile.byFullName(name);
    }

    @Override
    public Class byName(String name) throws ClassNotFoundException {
        return this.pluginProfile.byName(name);
    }

    @Override
    public Class bySimpleName(String name) throws ClassNotFoundException {
        return this.pluginProfile.bySimpleName(name);
    }

    @Override
    public void loadPlugins() {
        this.pluginProfile.loadPlugins();
    }

    @Override
    public <T> T makeObject(String className) throws Exception {
        return this.pluginProfile.makeObject(className);
    }

    @Override
    public <T> T makeObject(String className, Object... arguments) throws Exception {
        return this.pluginProfile.makeObject(className,arguments);
    }
    
}
