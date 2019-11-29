package com.acidmanic.utility.unirebase.commitmessageformatter;

import com.acidmanic.utility.unirebase.models.CommitData;

public interface CommitMessageFormatter {


    String format( CommitData commit);
    
}
