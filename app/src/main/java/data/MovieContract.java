package data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Douglas on 9/4/2015.
 */
public class MovieContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public MovieContract(){}

    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.example.douglas.popularmovies";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    public static final String PATH_MOVIE = "movie";
    public static final String PATH_REVIEW = "review";
    public static final String PATH_TRAILER = "trailer";

    /* Inner class that defines the table contents for Movie entries */
    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final String TABLE_NAME = "movie";
        public static final String COLUMN_NAME_MOVIE_ID = "movieid";
        public static final String COLUMN_NAME_MOVIE_TITLE = "movietitle";
        public static final String COLUMN_NAME_MOVIE_PATH = "moviepath";
        public static final String COLUMN_NAME_MOVIE_OVERVIEW = "movieoverview";
        public static final String COLUMN_NAME_MOVIE_VOTE_AVERAGE = "movievoteaverage";
        public static final String COLUMN_NAME_MOVIE_RELEASE_DATE = "moviereleasedate";

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    /* Inner class that defines the table contents for Review entries */
    public static final class ReviewEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEW).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEW;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEW;

        public static final String TABLE_NAME = "review";
        public static final String COLUMN_NAME_REVIEW_ID = "reviewid";
        public static final String COLUMN_NAME_REVIEW_MOVIE_FK = "movie_id";
        public static final String COLUMN_NAME_REVIEW_AUTHOR = "reviewauthor";
        public static final String COLUMN_NAME_REVIEW_CONTENT = "reviewcontent";
        public static final String COLUMN_NAME_REVIEW_URL = "reviewurl";

        public static Uri buildReviewUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class TrailerEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRAILER).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILER;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILER;

        public static final String TABLE_NAME = "trailer";
        public static final String COLUMN_NAME_TRAILER_ID = "trailerid";
        public static final String COLUMN_NAME_TRAILER_MOVIE_FK = "movie_id";
        public static final String COLUMN_NAME_ISO = "traileriso";
        public static final String COLUMN_NAME_KEY = "trailerkey";
        public static final String COLUMN_NAME_NAME = "trailername";
        public static final String COLUMN_NAME_SITE = "trailersite";
        public static final String COLUMN_NAME_SIZE = "trailersize";
        public static final String COLUMN_NAME_TYPE = "trailertype";

        public static Uri buildTrailerUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
