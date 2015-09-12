package entity;

/**
 * Created by Douglas on 8/17/2015.
 */
public class Trailer {

    public String mID;

    public String mISO;

    public String mKey;

    public String mName;

    public String mSite;

    public int mSize;

    public String mType;

    public String getType() {
        return mType;
    }

    public void setType(String Type) {
        this.mType = Type;
    }

    public int getSize() {
        return mSize;
    }

    public void setSize(int Size) {
        this.mSize = Size;
    }

    public String getSite() {
        return mSite;
    }

    public void setSite(String Site) {
        this.mSite = Site;
    }

    public String getName() {
        return mName;
    }

    public void setName(String Name) {
        this.mName = Name;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String Key) {
        this.mKey = Key;
    }

    public String getISO() {
        return mISO;
    }

    public void setISO(String ISO) {
        this.mISO = ISO;
    }

    public String getID() {
        return mID;
    }

    public void setID(String ID) {
        this.mID = ID;
    }
}
