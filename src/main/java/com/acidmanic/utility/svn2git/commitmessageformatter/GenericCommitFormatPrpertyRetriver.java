package com.acidmanic.utility.svn2git.commitmessageformatter;

import java.util.function.Function;

import com.acidmanic.utility.svn2git.models.CommitData;

class GenericCommitFormatPrpertyRetriver implements CommitFormatPropertyRetriver {



    private String propertyTag;

    protected Function<CommitData,String> retrive;


    @Override
    public String process(String inputString, CommitData data) {
        
        String value = this.retrive.apply(data);

        if (value==null) value = "";

        String ret = inputString.replace(propertyTag, value);

        return ret;
    }

    public GenericCommitFormatPrpertyRetriver(String propertyTag, Function<CommitData, String> retrive) {
        this.propertyTag = propertyTag;
        this.retrive = retrive;
    }

}
