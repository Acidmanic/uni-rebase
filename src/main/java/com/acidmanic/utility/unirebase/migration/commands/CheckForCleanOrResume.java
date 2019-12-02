/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.utility.unirebase.migration.commands;

import com.acidmanic.utility.unirebase.migration.MigrationCommand;
import com.acidmanic.utility.unirebase.models.MigrationContext;
import com.acidmanic.utility.unirebase.services.FilesystemService;

/**
 *
 * @author diego
 */
public class CheckForCleanOrResume implements MigrationCommand {

    @Override
    public void execute(MigrationContext context) {
        if (context.getConfig().isForceClean()) {
            FilesystemService fs = new FilesystemService();

            fs.deleteAway(context.getDestinationDirectory());
        }

    }

}
