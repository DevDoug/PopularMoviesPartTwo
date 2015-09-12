package tools;

import android.provider.MediaStore;
import android.text.TextUtils;

import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import entity.Movie;
import entity.MovieJSON;
import entity.Review;
import entity.ReviewJSON;
import entity.Trailer;
import entity.VideoJSON;

/**
 * Created by Douglas on 7/25/2015.
 */
public class MovieDataParser {

    public static ArrayList<Movie> getMovieData(MovieJSON moviedata) {

        ArrayList<Movie> movies = new ArrayList<>();

        for(LinkedTreeMap map : moviedata.result){
            Movie movie = new Movie();
            movie.setID((map.get("id") != null) ? map.get("id").toString() : null);
            movie.setTitle(map.get("title").toString());
            movie.setPath((map.get("poster_path") != null) ? map.get("poster_path").toString():null);
            movie.setOverview(map.get("overview").toString());
            movie.setReleaseDate(map.get("release_date").toString());
            movie.setVoteAverage(map.get("vote_average").toString());
            movies.add(movie);
        }

        return  movies;
    }

    public static ArrayList<Review> getReviewData(ReviewJSON reviewdata) {

        LinkedTreeMap treemap = reviewdata.result;
        ArrayList<LinkedTreeMap> subtree = (ArrayList<LinkedTreeMap>) treemap.get("results");
        ArrayList<Review> reviews = new ArrayList<>();

        for(LinkedTreeMap map : subtree){
            Review review = new Review();
            review.setReviewID(map.get("id").toString());
            review.setAuthor(map.get("author").toString());
            review.setContent(map.get("content").toString());
            review.setURL(map.get("url").toString());
            reviews.add(review);
        }

        return  reviews;
    }

    public static ArrayList<Trailer> getVideoData(VideoJSON videoData) {

        LinkedTreeMap treemap = videoData.videos;
        ArrayList<LinkedTreeMap> subtree = (ArrayList<LinkedTreeMap>) treemap.get("results");
        ArrayList<Trailer> trailers = new ArrayList<>();

        for(LinkedTreeMap map : subtree){
            Trailer trailer = new Trailer();
            trailer.setID(map.get("id").toString());
            trailer.setKey(map.get("key").toString());
            trailer.setName(map.get("name").toString());
            trailer.setSite(map.get("site").toString());
            trailer.setType(map.get("type").toString());
            trailers.add(trailer);
        }

        return  trailers;
    }
}
