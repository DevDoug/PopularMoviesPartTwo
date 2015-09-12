package popularmovieconstants;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import data.MovieContract;
import data.MovieDbHelper;
import entity.Movie;
import entity.Review;
import entity.Trailer;

/**
 * Created by Douglas on 7/28/2015.
 */
public class Constants {

    public static ListAdapter mMoviesAdapter;
    public static ArrayList<Movie> mMovies;
    public static ArrayList<Review> mReviews;
    public static ArrayList<Trailer> mTrailers;
    public static String YEAR_ONLY_DATE_FORMAT = "yyyy";

    public static ContentValues createMovieRecord(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry.COLUMN_NAME_MOVIE_ID,movie.getID());
        values.put(MovieContract.MovieEntry.COLUMN_NAME_MOVIE_TITLE, movie.getTitle());
        values.put(MovieContract.MovieEntry.COLUMN_NAME_MOVIE_PATH, movie.getPath());
        values.put(MovieContract.MovieEntry.COLUMN_NAME_MOVIE_OVERVIEW, movie.getOverview());
        values.put(MovieContract.MovieEntry.COLUMN_NAME_MOVIE_RELEASE_DATE, movie.getReleaseDate());
        values.put(MovieContract.MovieEntry.COLUMN_NAME_MOVIE_VOTE_AVERAGE, movie.getVoteAverage());
        return values;
    }

    public static ContentValues createReviewRecord(Review review, long movieid) {
        ContentValues values = new ContentValues();
        values.put(MovieContract.ReviewEntry.COLUMN_NAME_REVIEW_ID, review.getReviewID());
        values.put(MovieContract.ReviewEntry.COLUMN_NAME_REVIEW_MOVIE_FK, movieid);
        values.put(MovieContract.ReviewEntry.COLUMN_NAME_REVIEW_AUTHOR, review.getAuthor());
        values.put(MovieContract.ReviewEntry.COLUMN_NAME_REVIEW_CONTENT, review.getContent());
        values.put(MovieContract.ReviewEntry.COLUMN_NAME_REVIEW_URL, review.getURL());
        return values;
    }

    public static ContentValues createTrailerRecord(Trailer trailer, long movieid) {
        ContentValues values = new ContentValues();
        values.put(MovieContract.TrailerEntry.COLUMN_NAME_TRAILER_ID, trailer.getID());
        values.put(MovieContract.TrailerEntry.COLUMN_NAME_TRAILER_MOVIE_FK, movieid);
        values.put(MovieContract.TrailerEntry.COLUMN_NAME_ISO, trailer.getISO());
        values.put(MovieContract.TrailerEntry.COLUMN_NAME_KEY, trailer.getKey());
        values.put(MovieContract.TrailerEntry.COLUMN_NAME_NAME, trailer.getName());
        values.put(MovieContract.TrailerEntry.COLUMN_NAME_SITE, trailer.getSite());
        values.put(MovieContract.TrailerEntry.COLUMN_NAME_SIZE, trailer.getSize());
       // values.put(MovieContract.TrailerEntry.COLUMN_NAME_TYPE, trailer.getType());
        return values;
    }

    public static ContentValues[] createBulkTrailerValues(ArrayList<Trailer> trailers, long movieid) {
        ContentValues[] valueBundle = new ContentValues[trailers.size()];
        for(int i = 0; i < trailers.size();i++) {
            ContentValues contents = createTrailerRecord(trailers.get(i), movieid);
            valueBundle[i] = contents;
        }

        return valueBundle;
    }

    public static ContentValues[] createBulkReviewValues(ArrayList<Review> reviews, long movieid) {
        ContentValues[] valueBundle = new ContentValues[reviews.size()];
        for(int i = 0; i < reviews.size();i++){
            ContentValues contents = createReviewRecord(reviews.get(i), movieid);
            valueBundle[i] = contents;
        }

        return valueBundle;
    }

    public static ArrayList<Movie> retrieveMoviesFromCursor(Cursor cursor) {
        ArrayList<Movie> results = new ArrayList<>();
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++) {
            Movie movie = new Movie();
            movie.setID(cursor.getString(1));
            movie.setTitle(cursor.getString(2));
            movie.setPath(cursor.getString(3));
            movie.setOverview(cursor.getString(4));
            movie.setVoteAverage(cursor.getString(5));
            movie.setReleaseDate(cursor.getString(6));
            results.add(movie);
            cursor.moveToNext();
        }

        return results;
    }
}
