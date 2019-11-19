package com.acidmanic.utility.svn2git.commitconversion;

import com.acidmanic.utility.svn2git.models.Author;
import com.acidmanic.utility.svn2git.models.CommitData;
import com.acidmanic.utility.svn2git.services.AuthorsListFile;

public class AuthorsByLoginCommitRefiner implements CommitRefiner {



    private AuthorsListFile authorsList;

    public AuthorsByLoginCommitRefiner(AuthorsListFile authorsList) {
        this.authorsList = authorsList;
    }


    @Override
    public CommitData refine(CommitData commit) {

        Author author = authorsList.search(commit.getAuthorName());

        if (author != null){
            commit.setAuthorEmail(author.getEmail());

            commit.setAuthorName(author.getName());
        }

        return commit;
    }


}
