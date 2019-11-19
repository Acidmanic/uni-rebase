package com.acidmanic.utility.svn2git.services;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.acidmanic.utility.svn2git.models.Author;

public class AuthorsListFile {




    private List<Author> authors;
    private HashMap<String, Author> index;

    public AuthorsListFile() {
        this.authors = new ArrayList<>();
        this.index = new HashMap<>();
    }

    
    public List<Author> getAuthors(){
        return this.authors;
    }

    public static AuthorsListFile load(String path){
        AuthorsListFile ret = new AuthorsListFile();

        List<String> lines = readFile(path);

        for(String line : lines){
            Author author = parse(line);
            if(author != null){
                ret.authors.add(author);
                ret.index.put(author.getLogin().toLowerCase(), author);
            }
        }
        return ret;
    }

    public Author search(String login){

        String key = login.toLowerCase();

        if(this.index.containsKey(key)){
            return this.index.get(key);
        }

        return null;
    }

    private static Author parse(String line) {

        if (line != null && !line.isEmpty()){
            String[] loginParts = line.split("=");

            if( loginParts.length ==2  ){
                String login = loginParts[0].trim();

                String nameEmail = loginParts[1];

                int st = nameEmail.indexOf("<");

                String name, email;
                if(st > -1){
                    name = nameEmail.substring(0,st).trim();
                    int nd = nameEmail.indexOf(">",st);
                    if (nd >-1){
                        email = nameEmail.substring(st+1,nd);

                        Author ret = new Author();

                        ret.setEmail(email);
                        ret.setLogin(login);
                        ret.setName(name);

                        return ret;
                    }
                }

            }
        }

        return null;
    }

    private static List<String> readFile(String path) {
        
        try {
            return Files.readAllLines(Paths.get(path)) ;
        } catch (Exception e) {        }

        return new ArrayList<>();
    }
}
