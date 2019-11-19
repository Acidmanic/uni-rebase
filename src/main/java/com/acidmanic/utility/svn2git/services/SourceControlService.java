package com.acidmanic.utility.svn2git.services;

import java.io.File;
import java.util.ArrayList;

import com.acidmanic.utility.svn2git.models.CommitData;
import com.acidmanic.utility.svn2git.models.SCId;

public interface SourceControlService {





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