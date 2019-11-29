package com.acidmanic.utility.unirebase.commitconversion;

import com.acidmanic.utility.unirebase.models.CommitData;

public interface CommitRefiner{


    CommitData refine(CommitData commit);
}
