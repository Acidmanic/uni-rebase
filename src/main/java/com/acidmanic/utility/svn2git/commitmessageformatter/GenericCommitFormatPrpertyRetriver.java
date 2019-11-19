package com.acidmanic.utility.svn2git.commitmessageformatter;

import java.util.function.Function;

import com.acidmanic.utility.svn2git.models.CommitData;

class GenericCommitFormatPrpertyRetriver implements CommitFormatPropertyRetriver {



    private String propertyTag;

    protected Function<CommitData,String> retrive;


    

    @Override
    public String process(String inputString, CommitData data) {
        
        String ret = inputString.replace(propertyTag, this.retrive.apply(data));

        return ret;
    }

    public GenericCommitFormatPrpertyRetriver(String propertyTag, Function<CommitData, String> retrive) {
        this.propertyTag = propertyTag;
        this.retrive = retrive;
    }

}
