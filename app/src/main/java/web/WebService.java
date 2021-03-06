package web;

import java.net.URLEncoder;

import entity.MovieJSON;
import entity.ReviewJSON;
import entity.VideoJSON;
import retrofit.RestAdapter;

/**
 * Created by Douglas on 8/13/2015.
 */
public class WebService {

    RestAdapter mRetrofit;

    MovieDBService mService;

    public WebService() {
        mRetrofit = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org")
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .build();
        mService = mRetrofit.create(MovieDBService.class);
    }

    public MovieJSON getMovies(boolean sortType) {
        try {
            if(sortType)
                return mService.listMovies("popularity.desc&api_key=d273a1a1fb9390dab97ac0032b12366a");
            else
                return mService.listMovies("vote_count.desc&api_key=d273a1a1fb9390dab97ac0032b12366a");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ReviewJSON getReviews(String movieid) {
        try {
            return mService.listReviews(movieid);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public VideoJSON getVideos(String movieid) {
        try {
            return mService.listVideos(movieid);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
