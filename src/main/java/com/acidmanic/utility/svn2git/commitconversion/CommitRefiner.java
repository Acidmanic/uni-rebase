package com.acidmanic.utility.svn2git.commitconversion;

import com.acidmanic.utility.svn2git.models.CommitData;

public interface CommitRefiner{


    CommitData refine(CommitData commit);
}