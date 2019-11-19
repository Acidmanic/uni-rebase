package com.acidmanic.utility.svn2git.commitconversion;

import com.acidmanic.utility.svn2git.models.CommitData;

import org.tmatesoft.svn.core.SVNLogEntry;

public class SvnLogEntryCommitConvertor implements CommitConvertor<SVNLogEntry>{


    private String companyDomain="localhost";

    @Override
    public CommitData convert(SVNLogEntry entry) {
        CommitData commit = new CommitData();

        commit.setAuthorEmail(entry.getAuthor() + "@"+this.companyDomain);

        commit.setAuthorName(entry.getAuthor());

        commit.setDate(entry.getDate());

        commit.setMessage(entry.getMessage());

        commit.setIdentifier(String.format("%d", entry.getRevision()));

        return commit;
    }

    public SvnLogEntryCommitConvertor(String companyDomain) {
        this.companyDomain = companyDomain;
    }

    public SvnLogEntryCommitConvertor() {
    }

    public String getCompanyDomain() {
        return companyDomain;
    }

    public void setCompanyDomain(String companyDomain) {
        this.companyDomain = companyDomain;
    }

    



}
