package entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Douglas on 7/28/2015.
 */

public class Movie implements Parcelable {

    public String mID;

    public String mTitle;

    public String mPath;

    public String mOverview;

    public String mVoteAverage;

    public String mReleaseDate;

    public String getID(){
        return mID;
    }

    public void setID(String id){
        this.mID = id;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String Path) {
        this.mPath = Path;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String Title) {
        this.mTitle = Title;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String Overview) {
        this.mOverview = Overview;
    }

    public String getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(String VoteAverage) {
        this.mVoteAverage = VoteAverage;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String ReleaseDate) {
        this.mReleaseDate = ReleaseDate;
    }

    public Movie(){

    }

    public Movie(String mid, String title,String path, String overview, String voteaverage, String releasedate) {
        this.mID = mid;
        this.mTitle = title;
        this.mPath = path;
        this.mOverview = overview;
        this.mVoteAverage = voteaverage;
        this.mReleaseDate = releasedate;
    }

    private Movie(Parcel in) {
        mID = in.readString();
        mTitle = in.readString();
        mPath = in.readString();
        mOverview = in.readString();
        mReleaseDate = in.readString();
        mVoteAverage = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mID);
        out.writeString(mTitle);
        out.writeString(mPath);
        out.writeString(mOverview);
        out.writeString(mReleaseDate);
        out.writeString(mVoteAverage);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
