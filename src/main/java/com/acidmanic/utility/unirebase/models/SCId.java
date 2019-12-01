package com.acidmanic.utility.unirebase.models;


/***
 * Id object for any source control system
 */
public class SCId{


    public static final int SCID_TYPE_GIT  = 0;

    public static final int SCID_TYPE_SVN  = 1;

    public static final String SCID_NOT_A_GIT_HASH = "";

    public static final long SCID_NOT_A_SVN_REVISION = 0;

    private static final String FIRST_SVN_REVISION = "1";

    public static final int MINIMUM_GIT_HASH_LENGTH = 8;

    public static final SCId FIRST_GIT_COMMIT = createFirst(SCID_TYPE_GIT);

    public static final SCId FIRST_SVN_COMMIT = createFirst(SCID_TYPE_SVN);

    public static SCId create(String id) {
        try {
            long lId = Long.parseLong(id);
            return new SCId(lId);
        } catch (Exception e) {        }
        
        return new SCId(SCID_TYPE_GIT, id);
    }
    
    private int type;

    private String id;

    private boolean first;

    public SCId(int type, String id) {
        this.type = type;
        this.id = id;
        this.first = checkFirst(type,id);
    }

    public SCId() {
        this.type = SCID_TYPE_GIT;
        this.id = SCID_NOT_A_GIT_HASH;
        this.first = true;
    }

    /***
     * this constructor creates a Source control identifier of
     *  type SVN revision on given revision.
     * @param revision : The revision number
     */
    public SCId(long revision) {
        this.type = SCID_TYPE_SVN;
        this.id = String.format("%d", revision);
	}

	public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        this.first=checkFirst(this.type,id);
    }


    private boolean checkFirst(int type, String id) {

        if(type==SCID_TYPE_GIT){
            if(id==null || id.length()<MINIMUM_GIT_HASH_LENGTH) return true;
        }

        if(type==SCID_NOT_A_SVN_REVISION){
            try {
                long lid = Long.parseLong(id);
                return lid <2;
            } catch (Exception e) {
                return true;
            }
        }

        return false;
    }

    public String getGitHash() {
        if(this.type==SCID_TYPE_GIT)
            return this.id;
        return SCID_NOT_A_GIT_HASH;
    }

    public long getSvnRevision(){

        if(this.type==SCID_TYPE_SVN){
            try {
                return Long.parseLong(this.id);    
            } catch (Exception e) {            }
        }

        return SCID_NOT_A_SVN_REVISION;
    }
    
    public static SCId createFirst(int type){

        String id = "";

        if(type==SCID_TYPE_GIT) id = SCID_NOT_A_GIT_HASH;

        if(type==SCID_TYPE_SVN) id = FIRST_SVN_REVISION;

        SCId ret = new SCId(type, id);

        ret.first = true;

        return ret;
    }
    

    public boolean isFirst(){
        return this.first;
    }



    public String getGitShortHash(){
        if(this.id==null) return "";
        if(this.id.length()<MINIMUM_GIT_HASH_LENGTH) return this.id;
        return this.id.substring(0, MINIMUM_GIT_HASH_LENGTH);
    }

    public boolean equals(SCId id){
        
        if (id==null) return false;
        
        if(this.first && id.first) return true;

        if(this.type!=id.type) return false;

        if(this.type == SCID_TYPE_GIT){
            return this.getGitShortHash().equals(id.getGitShortHash());
        }

        if(type == SCID_TYPE_SVN){
            return this.getSvnRevision() == id.getSvnRevision();
        }

        return this.id.equals(id.id);

    }

	public boolean isEmpty() {
        if(this.type == SCID_TYPE_SVN && this.getSvnRevision()==0){
            return true;
        }
		return false;
    }
    
    @Override
    public String toString() {
        if(this.type == SCID_TYPE_GIT){
            return this.getGitShortHash();
        }
        if(this.type==SCID_TYPE_SVN){
            return this.id;
        }
        return String.format("SCID<%d:%s>", this.type,this.id);
    }

}
