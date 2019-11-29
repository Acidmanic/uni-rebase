package com.acidmanic.utility.svn2git.commands;

import com.acidmanic.commandline.commands.ApplicationWideTypeRegistery;

public class CommandRegistery {






    public static void register(){


        ApplicationWideTypeRegistery.makeInstance().registerClass(Svn2Git.class);;
    }

}