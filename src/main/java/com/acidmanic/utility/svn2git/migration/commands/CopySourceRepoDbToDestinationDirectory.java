package com.acidmanic.utility.svn2git.migration.commands;

import java.nio.file.FileSystem;

import com.acidmanic.utility.svn2git.migration.MigrationCommand;
import com.acidmanic.utility.svn2git.models.MigrationContext;
import com.acidmanic.utility.svn2git.services.FilesystemService;
import com.acidmanic.utility.svn2git.services.SourceControlService;

public class CopySourceRepoDbToDestinationDirectory implements MigrationCommand {

    @Override
    public void execute(MigrationContext context) {
        

        FilesystemService fs = new FilesystemService();

        context.getUpdateRepoLocations().getDatabseDir().mkdirs();

        try {

            fs.copyContent(context.getSourceRepoLocations().getDatabseDir()
                      ,context.getUpdateRepoLocations().getDatabseDir());
                      
        } catch (Exception e) {        }

        
        
    }

}