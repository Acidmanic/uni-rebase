

package com.acidmanic.utility.unirebase.commitconversion;

import com.acidmanic.utility.unirebase.models.CommitData;

public class DefaultCommitRefiner implements CommitRefiner {

    @Override
    public CommitData refine(CommitData commit) {
        return commit;
    }



}
