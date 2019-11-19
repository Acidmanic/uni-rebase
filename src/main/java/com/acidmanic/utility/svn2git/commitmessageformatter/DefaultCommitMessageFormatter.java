package com.acidmanic.utility.svn2git.commitmessageformatter;

import com.acidmanic.utility.svn2git.models.CommitData;

public class DefaultCommitMessageFormatter implements CommitMessageFormatter {

    @Override
    public String format(CommitData commit) {
        return commit.getMessage();
    }

}
