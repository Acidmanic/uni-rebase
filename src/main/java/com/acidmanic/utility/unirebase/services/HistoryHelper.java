package com.acidmanic.utility.unirebase.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.acidmanic.utility.unirebase.models.CommitData;
import com.acidmanic.utility.unirebase.models.SCId;

public class HistoryHelper {

    private HistoryHelper() {
    }

    public static void sort(List<CommitData> history) {

        history.sort(new Comparator<CommitData>() {

            @Override
            public int compare(CommitData o1, CommitData o2) {
                long dif = (o1.getDate().getTime() - o2.getDate().getTime());
                if (dif < -1000) {
                    dif = -1000;
                }
                if (dif > 1000) {
                    dif = 1000;
                }
                return (int) dif;
            }

        });
    }

    public static int skipToIndex(List<CommitData> allEntries, SCId fromId) {
        return skipToIndex(allEntries, fromId, (a,b) -> a.getId().compareTo(b.getId()));
    }
    
    private static int skipToIndex(List<CommitData> allEntries
                                   , SCId fromId
                                   , Comparator<SCId> comparator) {
        if (fromId.isFirst()) {
            return 0;
        }

        for (int i = 0; i < allEntries.size(); i++) {

            if (comparator.compare(allEntries.get(i).getIdentifier(),fromId)==0) {
                return i + 1;
            }
        }

        return allEntries.size();
    }
}
