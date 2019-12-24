/*

This file contains code copied from https://dzone.com/articles/get-all-classes-within-package

 */
package com.acidmanic.utility.unirebase.services;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 *
 * @author 80116
 */
public class Reflection {

    public static boolean doesImplement(Class type, Class iface) {
        Class[] interfaces = type.getInterfaces();

        for (Class i : interfaces) {
            if (i.equals(iface)) {
                return true;
            }
        }

        Class parent = type.getSuperclass();

        if (parent != null) {
            return doesImplement(parent, iface);
        }

        return false;
    }

    public static Class[] getClasses() {

        Package[] packages = Package.getPackages();

        List<Class> all = new ArrayList<>();
        
        for (Package p : packages) {
            try {
                ArrayList<Class> classes = getClasses(p.getName());
                
                all.addAll(classes);
                
            } catch (Exception e) {}

        }

        return all.toArray(new Class[all.size()]);
    }

    /**
     * Scans all classes accessible from the context class loader which belong
     * to the given package and sub-packages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static ArrayList<Class> getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList();
        while (resources.hasMoreElements()) {
            URL resource = (URL) resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        
        return classes;
    }

    /**
     * Recursive method used to find all classes in a given directory and
     * subdirs.
     *
     * @param directory The base directory
     * @param packageName The package name for classes found inside the base
     * directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    public static List findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
}
