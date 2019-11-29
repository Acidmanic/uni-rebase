package com.acidmanic.utility.unirebase.commitconversion;

import com.acidmanic.utility.unirebase.models.Author;
import com.acidmanic.utility.unirebase.models.CommitData;
import com.acidmanic.utility.unirebase.services.AuthorsListFile;

public class AuthorsByLoginCommitRefiner implements CommitRefiner {



    private AuthorsListFile authorsList;

    public AuthorsByLoginCommitRefiner(AuthorsListFile authorsList) {
        this.authorsList = authorsList;
    }

    public AuthorsByLoginCommitRefiner(String authorsFile) {
        this.authorsList = AuthorsListFile.load(authorsFile);
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
