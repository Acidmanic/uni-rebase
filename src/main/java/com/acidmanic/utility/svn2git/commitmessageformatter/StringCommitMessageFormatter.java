package com.acidmanic.utility.svn2git.commitmessageformatter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.acidmanic.utility.svn2git.models.CommitData;

public class StringCommitMessageFormatter {


    private static final List<CommitFormatPropertyRetriver> formatters = listFormatters();


    private static List<CommitFormatPropertyRetriver> listFormatters(){
        
        Class<CommitFormatPropertyRetriver> type = CommitFormatPropertyRetriver.class;

        List<CommitFormatPropertyRetriver> ret = new ArrayList<>();

        Field[] fields = type.getFields();

        for(Field f : fields){

            Class<?> fieldType = f.getType();

            if(instanceOf(fieldType, type)){
                try {
                    ret.add( (CommitFormatPropertyRetriver) f.get(null));
                } catch (Exception e) {                }
            }

        }

        return ret;
    }


    private static boolean instanceOf(Class<?> instance,Class<?> base){
        Class<?> parent = instance;

        while (parent!=null ){

            if(parent.equals(base)){
                return true;
            }

            parent = parent.getSuperclass();
        }

        return false;
    }


    public String format(String format, CommitData commit){

        String ret = new String(format);

        for(CommitFormatPropertyRetriver form : formatters)
            ret = form.process(ret,commit);

        return ret;
    }
}
