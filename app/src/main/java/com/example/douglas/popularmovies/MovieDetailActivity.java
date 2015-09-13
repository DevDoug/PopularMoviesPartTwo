package com.example.douglas.popularmovies;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import data.FetchMovieData;
import data.MovieContract;
import entity.Movie;
import entity.Review;
import entity.Trailer;
import listeners.ITaskCompleteListener;
import popularmovieconstants.Constants;

public class MovieDetailActivity extends Activity implements ITaskCompleteListener, View.OnClickListener {

    private static final String TAG = "MovieDetalActivity";
    public Movie mMovie = new Movie();

    public ArrayList<Review> mCurrentReviews;
    public ArrayList<Trailer> mCurrentTrailers;

    public TextView mMovieTitleText;
    public ImageView mMoviePoster;
    public TextView mMovieOverview;
    public TextView mMovieVoteAverageText;
    public TextView mMovieDateReleasedfield;

    public FetchMovieData mDataFetcher;
    public LinearLayout mMovieTrailerList;
    public LinearLayout mReviewList;

    public Button mReviewButton;
    public Button mTrailerButton;
    public Button mFavoriteButton;

    public boolean mIsFavorited;

    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            mMovie.setID(extras.getString(getString(R.string.moviedb_movie_id)));
            mMovie.setTitle(extras.getString(getString(R.string.moviedb_title_field)));
            mMovie.setPath(extras.getString(getString(R.string.moviedb_poster_path_field)));
            mMovie.setOverview(extras.getString(getString(R.string.moviedb_overview_field)));
            mMovie.setVoteAverage(extras.getString(getString(R.string.moviedb_vote_average_field)));
            mMovie.setReleaseDate(extras.getString(getString(R.string.moviedb_release_date_field)));
        }

        mMovieTitleText = (TextView) findViewById(R.id.movie_title);
        mMoviePoster = (ImageView) findViewById(R.id.movie_poster_detail);
        mMovieOverview = (TextView) findViewById(R.id.movie_overview);
        mMovieVoteAverageText = (TextView) findViewById(R.id.movie_average);
        mMovieDateReleasedfield = (TextView) findViewById(R.id.movie_release_date_field);
        mMovieTrailerList = (LinearLayout) findViewById(R.id.movie_trailer_list);
        mReviewList = (LinearLayout) findViewById(R.id.movie_review_list);
        mReviewButton = (Button) findViewById(R.id.switch_to_review_button);
        mTrailerButton = (Button) findViewById(R.id.switch_to_trailer_button);
        mFavoriteButton = (Button) findViewById(R.id.mark_as_favorites_button);

        mMovieTitleText.setText(mMovie.getTitle());
        mMovieOverview.setText(mMovie.getOverview());
        mMovieVoteAverageText.setText(String.format(getString(R.string.movie_rating_date), mMovie.getVoteAverage()));
        mMovieDateReleasedfield.setText(mMovie.getReleaseDate().split("-")[0]);

        String movieposterurllarge = (this.getString(R.string.moviedb_poster_base_url).concat(this.getString(R.string.moviedb_size_w500)).concat(mMovie.getPath()));
        Picasso.with(this).load(movieposterurllarge).into(mMoviePoster);

        mDataFetcher = new FetchMovieData(this,this);

        mDataFetcher.setReviewID(mMovie.getID());
        mDataFetcher.setTrailerID(mMovie.getID());

        mDataFetcher.getVideos();

        if (savedInstanceState == null ) {
            mDataFetcher.getReviews();
            mDataFetcher.getVideos();
        } else {
            mCurrentReviews = savedInstanceState.getParcelableArrayList("reviews");
            mCurrentTrailers = savedInstanceState.getParcelableArrayList("trailers");
            populateReviews();
            populateTrailers();
        }

        mReviewButton.setOnClickListener(this);
        mTrailerButton.setOnClickListener(this);
        mFavoriteButton.setOnClickListener(this);
    }

    //save our current movies so that we dont have to make multiple calls
    @Override
    protected void onSaveInstanceState(Bundle currentMovieBundle) {
        currentMovieBundle.putParcelableArrayList("reviews", mCurrentReviews);
        currentMovieBundle.putParcelableArrayList("trailers", mCurrentTrailers);
        super.onSaveInstanceState(currentMovieBundle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
        MenuItem item = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mIsFavorited)
            saveMovieToFavorites();
    }

    public void saveMovieToFavorites() {
        //check for movie in favorites to avoid duplicates
        Cursor moviecursor = this.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, null, MovieContract.MovieEntry.COLUMN_NAME_MOVIE_ID + " = " + mMovie.getID(), null, null, null);
        if(moviecursor.getCount() == 0) { // first time this movie has been favorited insert record
            Uri movieUri = this.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, Constants.createMovieRecord(mMovie));
            long movieid = ContentUris.parseId(movieUri);
            int insertedTrailerCount = this.getContentResolver().bulkInsert(MovieContract.TrailerEntry.CONTENT_URI, Constants.createBulkTrailerValues(Constants.mTrailers, movieid));
            int insertedReviewCount = this.getContentResolver().bulkInsert(MovieContract.ReviewEntry.CONTENT_URI, Constants.createBulkReviewValues(Constants.mReviews, movieid));

            if(insertedTrailerCount < 1)
                Log.e(TAG,"Trailer failed to insert");

            if(insertedReviewCount < 1)
                Log.e(TAG, " Review failed to insert");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        //share first trailer
        if(id == R.id.action_share) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.youtube_web_url) + mMovie.getID());
            shareIntent.setType("text/plain");
            startActivity(shareIntent);
            mShareActionProvider.setShareIntent(shareIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFetchMovieTaskCompleted() {

    }

    @Override
    public void onFetchReviewsTaskCompleted() {
        mCurrentReviews = Constants.mReviews;
        populateReviews();
    }

    @Override
    public void onFetchTrailerTaskCompleted() {
        mCurrentTrailers = Constants.mTrailers;
        populateTrailers();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_to_review_button:
                switchadaptertoreviews();
                break;
            case R.id.switch_to_trailer_button:
                switchadaptertotrailers();
                break;
            case R.id.mark_as_favorites_button:
                mIsFavorited = !mIsFavorited;
            default:
                break;
        }
    }

    public void switchadaptertoreviews(){
        mReviewButton.setVisibility(View.GONE);
        mTrailerButton.setVisibility(View.VISIBLE);

        mMovieTrailerList.setVisibility(View.INVISIBLE);
        mReviewList.setVisibility(View.VISIBLE);
    }

    public void switchadaptertotrailers(){
        mReviewButton.setVisibility(View.VISIBLE);
        mTrailerButton.setVisibility(View.GONE);

        mMovieTrailerList.setVisibility(View.VISIBLE);
        mReviewList.setVisibility(View.INVISIBLE);
    }

    public void populateReviews(){
        for(int i = 0; i < mCurrentReviews.size();i++){
            LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = vi.inflate(R.layout.movie_review_list_item, null);

            TextView reviewtextview = (TextView) v.findViewById(R.id.movie_review_name);
            String reviewtext = mCurrentReviews.get(i).getContent();
            if(reviewtext != null)
                reviewtextview.setText(reviewtext);

            // insert into main view
            mReviewList.addView(v);
        }
    }

    public void populateTrailers(){
        for(int i = 0; i < Constants.mTrailers.size();i++){
            LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = vi.inflate(R.layout.movie_trailer_list_item, null);

            TextView trailername = (TextView) v.findViewById(R.id.trailer_name);
            trailername.setText(Constants.mTrailers.get(i).getName());
            trailername.setTag(Constants.mTrailers.get(i).getID());

            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //play trailer
                    TextView movietitleview = (TextView) v.findViewById(R.id.trailer_name);
                    String movieid = movietitleview.getTag().toString();
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.youtube_app_uri) + movieid));
                        startActivity(intent);
                    } catch (ActivityNotFoundException ex) {
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(getString(R.string.youtube_web_url) + movieid));
                        startActivity(intent);
                    }
                }
            });

            // insert into main view
            mMovieTrailerList.addView(v);
        }
    }
}
