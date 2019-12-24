/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.utility.unirebase.services;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 80116
 */
public class SourceControlServiceFatory {
    
    
    private List<Constructor<SourceControlService>> constructors;
    
    private static SourceControlServiceFatory instance = null;
    
    
    public static synchronized SourceControlServiceFatory getInstance(){
        if(instance == null ){
            instance = new SourceControlServiceFatory();
        }
        
        return instance;
    }
    
    
    
    private SourceControlServiceFatory(){
        
        
        this.constructors = new ArrayList<>();
        
        Class[] classes = Reflection.getClasses(this);
        
        for(Class c: classes){
            if(Reflection.doesImplement(c, SourceControlService.class)){
                try {
                    Constructor<SourceControlService> cons = c.getConstructor(File.class);
                    
                    if(cons != null){
                        this.constructors.add(cons);
                    }
                    
                } catch (Exception ex) {} 
            }
        }
    }
    
    
    public SourceControlService make(File projectRoot){
        
        for(Constructor<SourceControlService> cons : this.constructors){
            
            try {
                SourceControlService service = cons.newInstance(projectRoot);
                
                if (service != null  &&  service.isRepo()){
                    return service;
                }
            } catch (Exception e) {
            }
            
        }
        return new NullSourceControlService(projectRoot);
    }
}
