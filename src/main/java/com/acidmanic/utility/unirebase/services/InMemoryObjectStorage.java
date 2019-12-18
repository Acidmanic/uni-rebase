package com.acidmanic.utility.unirebase.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryObjectStorage{


    private HashMap<String,Object> data;


    public InMemoryObjectStorage() {
        this.data = new HashMap<>();
    }

    public <T> void put(String tag,T object){
        this.data.put(tag,object);
    }


    public <T> T get(String tag){
        if(this.data.containsKey(tag)){
            return (T) this.data.get(tag);
        }

        return null;
    }


    public <T> void put(Class<?> tagByType,T object){
        this.data.put(tagByType.getName(),object);
    }


    public <T> T get(Class<?> tagByType){
        String tag = tagByType.getName();
        if(this.data.containsKey(tag)){
            return (T) this.data.get(tag);
        }

        return null;
    }


    public List<String> tags(){
        List<String> ret = new ArrayList<>();

        this.data.keySet().forEach((key) -> ret.add(key));

        return ret;
    }

}