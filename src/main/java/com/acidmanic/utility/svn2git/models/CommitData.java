package com.acidmanic.utility.svn2git.models;

import java.util.Date;

public class CommitData{


    private String message;

    private String authorName;

    private String authorEmail;

    private Date date;

    private SCId identifier;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public SCId getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(SCId identifier) {
        this.identifier = identifier;
    }

}
