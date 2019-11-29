package com.acidmanic.utility.unirebase.commitmessageformatter;

import com.acidmanic.utility.unirebase.models.CommitData;

public class DefaultCommitMessageFormatter implements CommitMessageFormatter {

    @Override
    public String format(CommitData commit) {
        return commit.getMessage();
    }

}
