

package com.acidmanic.utility.svn2git.commitconversion;

import com.acidmanic.utility.svn2git.models.CommitData;

public class DefaultCommitRefiner implements CommitRefiner {

    @Override
    public CommitData refine(CommitData commit) {
        return commit;
    }



}
