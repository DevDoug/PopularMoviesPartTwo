package entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Douglas on 7/28/2015.
 */

public class Movie {

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

}
