package com.acidmanic.utility.unirebase.commitmessageformatter;


import com.acidmanic.utility.unirebase.models.CommitData;

public interface CommitFormatPropertyRetriver {


    public static final CommitFormatPropertyRetriver CommitMessage 
            = new GenericCommitFormatPrpertyRetriver("{{MESSAGE}}", (CommitData d) -> d.getMessage());

    public static final CommitFormatPropertyRetriver CommitDate 
        = new GenericCommitFormatPrpertyRetriver("{{DATE}}", (CommitData d) -> d.getDate().toString());

    public static final CommitFormatPropertyRetriver CommitAuthorName 
        = new GenericCommitFormatPrpertyRetriver("{{AUTHOR}}", (CommitData d) -> d.getAuthorName());

    public static final CommitFormatPropertyRetriver CommitAuthorEmail 
        = new GenericCommitFormatPrpertyRetriver("{{EMAIL}}", (CommitData d) -> d.getAuthorEmail());


    public static final CommitFormatPropertyRetriver CommitIdentifer 
        = new GenericCommitFormatPrpertyRetriver("{{ID}}", (CommitData d) -> d.getIdentifier().getId());

    public static final CommitFormatPropertyRetriver CommitIdentiferShortHash
        = new GenericCommitFormatPrpertyRetriver("{{HASH8}}", (CommitData d) -> d.getIdentifier().getGitShortHash());
    
    String process(String inputString,CommitData data);

    


}
