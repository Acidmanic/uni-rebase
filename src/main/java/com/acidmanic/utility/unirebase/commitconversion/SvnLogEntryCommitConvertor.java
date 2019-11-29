package com.acidmanic.utility.unirebase.commitconversion;

import com.acidmanic.utility.unirebase.models.CommitData;
import com.acidmanic.utility.unirebase.models.SCId;

import org.tmatesoft.svn.core.SVNLogEntry;

public class SvnLogEntryCommitConvertor implements CommitConvertor<SVNLogEntry>{



    @Override
    public CommitData convert(SVNLogEntry entry) {
        CommitData commit = new CommitData();

        commit.setAuthorEmail("");

        commit.setAuthorName(entry.getAuthor());

        commit.setDate(entry.getDate());

        commit.setMessage(entry.getMessage());

        SCId id = new SCId(entry.getRevision());

        commit.setIdentifier(id);

        return commit;
    }

  

    public SvnLogEntryCommitConvertor() {
    }

}
