package entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Douglas on 8/17/2015.
 */
public class Trailer implements Parcelable {

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


    public Trailer(){

    }

    public Trailer(String tid, String iso,String key, String name, String site, int size, String type) {
        this.mID = tid;
        this.mISO = iso;
        this.mKey = key;
        this.mName = name;
        this.mSite = site;
        this.mSize = size;
        this.mType = type;
    }

    private Trailer(Parcel in) {
        mID = in.readString();
        mISO = in.readString();
        mName = in.readString();
        mSite = in.readString();
        mSize = in.readInt();
        mType = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mID);
        out.writeString(mISO);
        out.writeString(mName);
        out.writeString(mSite);
        out.writeInt(mSize);
        out.writeString(mType);
    }

    public static final Parcelable.Creator<Trailer> CREATOR = new Parcelable.Creator<Trailer>() {
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };
}
