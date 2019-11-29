package com.acidmanic.utility.svn2git.exceptions;



public class NotImplementedYetException extends Exception {


    private static final long serialVersionUID = -7405518117741087672L;

    public NotImplementedYetException() {
        super("This method is not implemented yet.");
    }
}