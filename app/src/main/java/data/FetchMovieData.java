package data;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;
import com.example.douglas.popularmovies.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Objects;

import entity.Movie;
import entity.MovieJSON;
import entity.ReviewJSON;
import entity.VideoJSON;
import listeners.ITaskCompleteListener;
import popularmovieconstants.Constants;
import tools.MovieDataParser;
import web.WebService;

/**
 * Created by Douglas on 7/28/2015.
 */
public class FetchMovieData {

    public Context mContext;
    private MovieJSON mMovieData;
    private ReviewJSON mMovieReviews;
    private VideoJSON mMovieVideos;
    public static boolean mSortByMostPopular = true;
    ITaskCompleteListener mTaskCompleteListener;
    public String reviewid;
    public String trailerid;

    public FetchMovieData(Context context,ITaskCompleteListener listener) {
        mContext = context;
        this.mTaskCompleteListener = listener;
    }

    public void getMovies() {
        new FetchMovies().execute();
    }

    public void getReviews() {
        new FetchReviews().execute();
    }

    public void setReviewID(String reviewid){
        this.reviewid = reviewid;
    }

    public void setTrailerID(String trailerid){
        this.trailerid = trailerid;
    }

    public void getVideos() {
        new FetchTrailers().execute();
    }

    private class FetchMovies extends AsyncTask<String, Void, Void > {

        @Override
        protected Void doInBackground(String... params) {
            WebService service = new WebService();
            mMovieData = service.getMovies(mSortByMostPopular);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(mMovieData != null)
                Constants.mMovies = MovieDataParser.getMovieData(mMovieData);

            mTaskCompleteListener.onFetchMovieTaskCompleted(); //Task completed alert UI that we have our data
        }
    }

    private class FetchReviews extends AsyncTask<String, Void, Void > {

        @Override
        protected Void doInBackground(String... params) {
            WebService service = new WebService();
            mMovieReviews = service.getReviews(reviewid);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(mMovieReviews != null)
                Constants.mReviews = MovieDataParser.getReviewData(mMovieReviews);

            mTaskCompleteListener.onFetchReviewsTaskCompleted(); //Task completed alert UI that we have our data
        }
    }

    private class FetchTrailers extends AsyncTask<String, Void, Void > {

        @Override
        protected Void doInBackground(String... params) {
            WebService service = new WebService();
            mMovieVideos = service.getVideos(trailerid);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(mMovieVideos != null)
                Constants.mTrailers = MovieDataParser.getVideoData(mMovieVideos);

            mTaskCompleteListener.onFetchTrailerTaskCompleted(); //Task completed alert UI that we have our data
        }
    }
}
