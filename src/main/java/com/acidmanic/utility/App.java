package com.acidmanic.utility;

import com.acidmanic.utility.svn2git.commands.CommandRegistery;

import com.acidmanic.commandline.application.ExecutionEnvironment;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ExecutionEnvironment environment = new ExecutionEnvironment();

        CommandRegistery.register();

        environment.execute(args);
    }
}
