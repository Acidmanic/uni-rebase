package com.acidmanic.utility.svn2git.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.acidmanic.utility.svn2git.models.CommitData;
import com.acidmanic.utility.svn2git.models.SCId;

public class HistoryHelper {



    private HistoryHelper() {
    }



    public static void sort(List<CommitData> history){

        history.sort(new Comparator<CommitData>() {

            @Override
            public int compare(CommitData o1, CommitData o2) {
                long dif = (o1.getDate().getTime() - o2.getDate().getTime());
                if(dif<-1000) dif =-1000;
                if(dif>1000) dif = 1000;
                return (int)dif ;
            }
            
        });
    }



	public static int skipToIndex(ArrayList<CommitData> allEntries, SCId fromId) {
        if(fromId.isFirst()) return 0;

        for(int i=0;i<allEntries.size();i++){
        
            if(allEntries.get(i).getIdentifier().equals(fromId)){
                return i+1;
            }
        }
        
        return allEntries.size();
	}
}
