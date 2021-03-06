package com.acidmanic.utility.unirebase.services;

import java.io.File;
import java.util.ArrayList;

import com.acidmanic.utility.unirebase.models.CommitData;
import com.acidmanic.utility.unirebase.models.SCId;

public interface SourceControlService {

    public static final SourceControlService NULL = new NullSourceControlService(new File("."));

    void recallProjectState(SCId id) throws Exception;

    ArrayList<CommitData> listAllCommits() throws Exception;

    public File getRootDirectory();

    public void dispose();

    public void ignore(File file) throws Exception;

    public void acceptAllChanges(String commitMessage) throws Exception;

    public void acceptAllChanges(CommitData commit) throws Exception;

    public void initialize() throws Exception;

    public boolean isRepo();
    
}