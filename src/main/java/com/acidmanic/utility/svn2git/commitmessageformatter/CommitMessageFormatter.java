package com.acidmanic.utility.svn2git.commitmessageformatter;

import com.acidmanic.utility.svn2git.models.CommitData;

public interface CommitMessageFormatter {


    String format( CommitData commit);
    
}
