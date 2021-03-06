package com.acidmanic.utility.unirebase.migration.commands;


import com.acidmanic.utility.unirebase.migration.MigrationCommand;
import com.acidmanic.utility.unirebase.models.MigrationContext;
import com.acidmanic.utility.unirebase.services.FilesystemService;

public class CopySourceRepoDbToDestinationDirectory implements MigrationCommand {

    @Override
    public void execute(MigrationContext context) {
        

        FilesystemService fs = new FilesystemService();

        context.getUpdateRepoLocations().getDatabseDir().mkdirs();

        try {

            fs.copyContent(context.getSourceRepoLocations().getDatabseDir()
                      ,context.getUpdateRepoLocations().getDatabseDir());
                      
        } catch (Exception e) {     
            context.getLogger().accept("Problem Copying files: " + e.getMessage());
        }

        
        
    }

}