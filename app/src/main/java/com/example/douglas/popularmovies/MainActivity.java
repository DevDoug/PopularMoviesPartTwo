package com.example.douglas.popularmovies;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import adapters.MovieReviewAdapter;
import adapters.MovieTrailerAdapter;
import data.MovieContract;
import listeners.ITaskCompleteListener;
import adapters.MovieAdapter;
import entity.Movie;
import data.FetchMovieData;
import popularmovieconstants.Constants;

public class MainActivity extends Activity implements MoviePosterGridFragment.OnFragmentInteractionListener, MovieDetailFragment.OnFragmentInteractionListener, AdapterView.OnItemClickListener, ITaskCompleteListener {

    private GridView mMoviesGrid;
    public FetchMovieData mDataFetcher;
    public MovieDetailFragment movieDetailFrag;
    public MovieTrailerAdapter mMovieTrailerAdapter;
    public MovieReviewAdapter mMovieReviewAdapter;
    public boolean isaMovieSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMoviesGrid = (GridView) findViewById(R.id.movie_list_grid);
        movieDetailFrag = (MovieDetailFragment) getFragmentManager().findFragmentById(R.id.details_frag);
        mMoviesGrid.setOnItemClickListener(this);
        mDataFetcher = new FetchMovieData(this,this);

        //first determine if they are online, if they are query the api for all movies if not display just their favorites
        ConnectivityManager connectionmanager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectionmanager.getActiveNetworkInfo();

        if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            // Check whether we're recreating a previously destroyed instance
            if (savedInstanceState != null) {
                // Restore value of members from saved state
                this.onFetchMovieTaskCompleted();
            } else {
               mDataFetcher.getMovies();
            }
        }else {
            showFavorites();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
        if (id == R.id.action_sort) {
            mDataFetcher.getMovies();
            mDataFetcher.mSortByMostPopular = !mDataFetcher.mSortByMostPopular;
            if(mDataFetcher.mSortByMostPopular)
                item.setTitle("sort by most popular");
            else
                item.setTitle("sort by highest voted");
        }
        if(id == R.id.action_show_favorites) {
            showFavorites();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //onclicking an item go to the detail view with and populate it with that moves data
        if (movieDetailFrag == null) {
            Intent movieDetailsIntent = new Intent(this,MovieDetailActivity.class);
            Movie selectedmovie = (Movie) view.findViewById(R.id.movie_image).getTag();
            movieDetailsIntent.putExtra(getString(R.string.moviedb_movie_id), selectedmovie.getID());
            movieDetailsIntent.putExtra(getString(R.string.moviedb_title_field),selectedmovie.getTitle());
            movieDetailsIntent.putExtra(getString(R.string.moviedb_poster_path_field),selectedmovie.getPath());
            movieDetailsIntent.putExtra(getString(R.string.movie_image_field),selectedmovie.getPath());
            movieDetailsIntent.putExtra(getString(R.string.moviedb_overview_field), selectedmovie.getOverview());
            movieDetailsIntent.putExtra(getString(R.string.moviedb_vote_average_field),selectedmovie.getVoteAverage());
            movieDetailsIntent.putExtra(getString(R.string.moviedb_release_date_field), selectedmovie.getReleaseDate());
            startActivity(movieDetailsIntent);
        } else {
            // tell the fragment to update
            movieDetailFrag.updateContent(position);
        }
    }

    @Override
    public void onFetchMovieTaskCompleted() {
        if(Constants.mMovies != null) {
            Constants.mMoviesAdapter = new MovieAdapter(this, Constants.mMovies);
            mMoviesGrid.setAdapter(Constants.mMoviesAdapter);
        }

        if(movieDetailFrag != null && !isaMovieSelected){
            movieDetailFrag.updateContent(0); //select the first movie automatically
            isaMovieSelected = true;
        }
    }

    @Override
    public void onFetchReviewsTaskCompleted() {
        mMovieReviewAdapter = new MovieReviewAdapter(this, Constants.mReviews);
        if(movieDetailFrag != null){
            ListView MovieReviewList =(ListView) movieDetailFrag.getView().findViewById(R.id.movie_review_list);
            MovieReviewList.setAdapter(mMovieReviewAdapter);
        }
    }

    @Override
    public void onFetchTrailerTaskCompleted() {
        mMovieTrailerAdapter = new MovieTrailerAdapter(this, Constants.mTrailers);
        if(movieDetailFrag != null){
            ListView MovieTrailerList =(ListView) movieDetailFrag.getView().findViewById(R.id.movie_trailer_list);
            MovieTrailerList.setAdapter(mMovieTrailerAdapter);
        }
    }

    public void showFavorites(){
        Cursor moviesCursor = this.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, null, null, null, null); //query the movie table ( all nulls will result in SELECT * FROM movies )
        Constants.mMovies = Constants.retrieveMoviesFromCursor(moviesCursor);
        this.onFetchMovieTaskCompleted();
    }

    @Override
    public void onFragmentInteraction(View v){
        switch (v.getId()){
            case R.id.switch_to_review_button:
                switchadaptertoreviews();
                break;
            case R.id.switch_to_trailer_button:
                switchadaptertotrailers();
                break;
        }

    }

    @Override
    public void onFragmentInteraction(View v, Movie m) {
        switch (v.getId()){
            case R.id.mark_as_favorites_button:
                saveMovieToFavorites(m);
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void saveMovieToFavorites(Movie m) {
        //check for movie in favorites to avoid duplicates
        Cursor moviecursor = this.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,null,MovieContract.MovieEntry.COLUMN_NAME_MOVIE_ID + " = " + m.getID(), null,null,null);
        if(moviecursor.getCount() == 0) { // first time this movie has been favorited insert record
            Uri movieUri = this.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, Constants.createMovieRecord(m));
            long movieid = ContentUris.parseId(movieUri);
            int insertedTrailerCount = this.getContentResolver().bulkInsert(MovieContract.TrailerEntry.CONTENT_URI, Constants.createBulkTrailerValues(Constants.mTrailers, movieid));
            int insertedReviewCount = this.getContentResolver().bulkInsert(MovieContract.ReviewEntry.CONTENT_URI, Constants.createBulkReviewValues(Constants.mReviews, movieid));
        }
    }

    public void retrieveReviewsAndTrailersForFragment(String id){
        mDataFetcher.setReviewID(id);
        mDataFetcher.setTrailerID(id);
        mDataFetcher.getVideos();
        mDataFetcher.getReviews();
    }

    public void switchadaptertoreviews(){
        if(movieDetailFrag != null){
            Button trailerbutton = (Button) movieDetailFrag.getView().findViewById(R.id.switch_to_trailer_button);
            Button reviewbutton = (Button) movieDetailFrag.getView().findViewById(R.id.switch_to_review_button);
            ListView movieTrailerList = (ListView) movieDetailFrag.getView().findViewById(R.id.movie_trailer_list);
            ListView reviewList = (ListView) movieDetailFrag.getView().findViewById(R.id.movie_review_list);

            reviewbutton.setVisibility(View.GONE);
            trailerbutton.setVisibility(View.VISIBLE);

            movieTrailerList.setVisibility(View.INVISIBLE);
            reviewList.setVisibility(View.VISIBLE);
        }
    }

    public void switchadaptertotrailers(){
        if(movieDetailFrag != null){
            Button trailerbutton = (Button) movieDetailFrag.getView().findViewById(R.id.switch_to_trailer_button);
            Button reviewbutton = (Button) movieDetailFrag.getView().findViewById(R.id.switch_to_review_button);
            ListView movieTrailerList = (ListView) movieDetailFrag.getView().findViewById(R.id.movie_trailer_list);
            ListView reviewList = (ListView) movieDetailFrag.getView().findViewById(R.id.movie_review_list);

            reviewbutton.setVisibility(View.VISIBLE);
            trailerbutton.setVisibility(View.GONE);

            movieTrailerList.setVisibility(View.VISIBLE);
            reviewList.setVisibility(View.INVISIBLE);
        }
    }
}
