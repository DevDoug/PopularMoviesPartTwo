package entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Douglas on 8/17/2015.
 */
public class Review implements Parcelable {

    public String mReviewID;

    public String mAuthor;

    public String mContent;

    public String mURL;

    public String getReviewID() {
        return mReviewID;
    }

    public void setReviewID(String reviewID) {
        mReviewID = reviewID;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    public String getURL() {
        return mURL;
    }

    public void setURL(String mURL) {
        this.mURL = mURL;
    }

    public Review(){

    }

    public Review(String rid, String author,String content, String url) {
        this.mReviewID = rid;
        this.mAuthor = author;
        this.mContent = content;
        this.mURL = url;
    }

    private Review(Parcel in) {
        mReviewID = in.readString();
        mAuthor = in.readString();
        mContent = in.readString();
        mURL = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mReviewID);
        out.writeString(mAuthor);
        out.writeString(mContent);
        out.writeString(mURL);
    }

    public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

}
