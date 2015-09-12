package web;

import java.util.List;

import entity.Movie;
import entity.MovieJSON;
import entity.ReviewJSON;
import entity.VideoJSON;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Douglas on 8/13/2015.
 */
public interface MovieDBService {
    @GET("/3/discover/movie")
    MovieJSON listMovies(@Query(value = "sort_by",encodeValue=false) String switchterm);

    @GET("/3/movie/{id}?api_key=d273a1a1fb9390dab97ac0032b12366a&append_to_response=reviews")
    ReviewJSON listReviews(@Path("id") String switchterm);

    @GET("/3/movie/{id}?api_key=d273a1a1fb9390dab97ac0032b12366a&append_to_response=videos")
    VideoJSON listVideos(@Path("id") String switchterm);
}
