package com.acidmanic.utility.svn2git.services;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.HashMap;

public class IdWatcher<T> {

    private T object;
    private T backup;
    private Thread thread;
    private boolean keepGoing;

    private Comparator<T> changeDetector;

    private Runnable onObjectChanged = new Runnable() {

        @Override
        public void run() {
            System.out.println("Given Object has been changed");
        }
    };

    public IdWatcher() {
        super();
    }

    public IdWatcher(T object, Comparator<T> changeDetector) throws Exception {
        this.object = object;
        this.backup = clone(object);
        this.changeDetector = changeDetector;
    }

    public void stop() {
        keepGoing = false;
        if (thread != null) {
            while (thread.isAlive())
                ;
        }
        thread = null;
    }

    public void start() {
        stop();
        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (keepGoing) {
                    try {
                        if(changeDetector.compare(object, backup)!=0){
                            onObjectChanged.run();
                            backup= IdWatcher.this.clone(object);
                        }

                        Thread.sleep(25);
                    } catch (Exception e) {}
                }
            }
        });
        keepGoing = true;
        thread.start();
    }

    public T getObject() {
        return object;
    }

    public Comparator<T> getChangeDetector() {
        return changeDetector;
    }

    public void setChangeDetector(Comparator<T> changeDetector) {
        this.changeDetector = changeDetector;
    }

    public Runnable getOnObjectChanged() {
        return onObjectChanged;
    }

    public void setOnObjectChanged(Runnable onObjectChanged) {
        this.onObjectChanged = onObjectChanged;
    }

    private class GetterResult {
        public String name;
        public boolean isGetter;
    }

    private T clone(Object object) throws Exception {

        Class<T> type = (Class<T>) object.getClass();

        T ret = type.newInstance();

        Method methods[] = type.getDeclaredMethods();


        HashMap<String,Method> methMap = new HashMap<>();

        for(Method method: methods){
            methMap.put(getMethodKey(method), method);
        }

        for(Method method: methods){

            try {
                GetterResult result = isGetter(method);
            
                if(result.isGetter){
                    Method setter = findSetterFor(methMap, result.name,method.getReturnType().getName());
                    if(setter != null){
                        Object value = method.invoke(object);
                        setter.invoke(ret, value);
                    }
                }
            } catch (Exception e) {
            }
        }

        return ret;

    }

    private Method findSetterFor(HashMap<String, Method> methMap,String name,String type) {
        String[] names = {
            name, "set" + name.toUpperCase().charAt(0)+name.substring(1,name.length())
        };

        for(String n:names){
            String key = getMethodKey(n,1,type);
            if(methMap.containsKey(key)){
               return methMap.get(key);
            }
        }

        return null;
    }

    private String getMethodKey(String name,int paramCount,String type){
        return name + ":" + paramCount+":"+type;
    }

    private String getMethodKey(Method method) {
        String type = "null";
        if(method.getParameterCount()>0){
            type = method.getParameterTypes()[0].getName();
        }
        return getMethodKey(method.getName(),method.getParameterCount(),type);
        
    }

    private IdWatcher<T>.GetterResult isGetter(Method method) {
        GetterResult res = new GetterResult();
        res.isGetter = false;
        res.name = method.getName();
        if(method.getParameterCount()==0){
            res.isGetter = true;
            if(res.name.startsWith("get")) {
                res.name = res.name.substring(3,res.name.length());
            }else if(res.name.startsWith("is")) {
                res.name = res.name.substring(2,res.name.length());
            }
        }
        return res;
    }

    
}