package com.acidmanic.utility.unirebase.commands;

import com.acidmanic.commandline.commands.ApplicationWideTypeRegistery;

public class CommandRegistery {






    public static void register(){


        ApplicationWideTypeRegistery.makeInstance().registerClass(Svn2Git.class);
        
        ApplicationWideTypeRegistery.makeInstance().registerClass(Git2Git.class);
    }

}