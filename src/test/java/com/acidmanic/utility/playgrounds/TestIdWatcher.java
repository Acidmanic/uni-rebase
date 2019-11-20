package com.acidmanic.utility.playgrounds;

import java.util.Date;

import com.acidmanic.utility.svn2git.models.Author;
import com.acidmanic.utility.svn2git.models.SCId;
import com.acidmanic.utility.svn2git.services.IdWatcher;

public class TestIdWatcher {



    public static void main(String[] args) throws Exception {
        


        SCId id = new SCId(1, "12");
        Author auth = new Author();

        auth.setEmail("morgh@pashmak.com");
        auth.setLogin("morgh");
        auth.setName("pashmak");

//        IdWatcher<Author> watcher = new IdWatcher<Author>(auth, (id1,id2) -> id1.getEmail().compareTo(id2.getEmail()));
        IdWatcher<SCId> watcher = new IdWatcher<SCId>(id, (id1,id2) -> id1.getId().compareTo(id2.getId()));

        watcher.start();




        String command = "";

        while( "exit".compareTo(command)!=0){

            command = "";

            int in =0;
            while(in != 13){
                in = System.in.read();

                if(in >-1){
                    command += (char) in;
                }
                try {
                    Thread.sleep(5);
                } catch (Exception e) {
                }
            }

            command = command.replaceAll("\\s", "");
            if(command.compareTo("change")==0){            
                id.setId(id.getId()+"0");
                auth.setEmail(new Date().toString()+"@pashmak.com");
            }

            if(command.compareTo("print")==0){            
                System.out.println(id.toString());
                System.out.println(auth.getEmail());
            }
            

        }
    }
}