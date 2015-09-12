package entity;

/**
 * Created by Douglas on 8/17/2015.
 */
public class Review {

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
}
