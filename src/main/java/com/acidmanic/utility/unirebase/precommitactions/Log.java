/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.utility.unirebase.precommitactions;

import com.acidmanic.utility.unirebase.commitmessageformatter.CommitMessageFormatter;
import com.acidmanic.utility.unirebase.models.CommitData;
import com.acidmanic.utility.unirebase.services.PreCommitHelperToolset;
import java.io.File;
import java.util.function.Consumer;

/**
 *
 * @author 80116
 */
public class Log implements PreCommitAction{

    @Override
    public void onPreCommit(PreCommitHelperToolset helpers, File sourceDirectory, Consumer<String> logger, CommitData commit, CommitMessageFormatter formatter) {
        logger.accept("Commiting: " + commit.getMessage());
    }
    
}
