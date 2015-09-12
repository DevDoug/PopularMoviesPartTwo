package com.example.douglas.popularmovies;

import android.app.Activity;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import adapters.MovieReviewAdapter;
import adapters.MovieTrailerAdapter;
import data.FetchMovieData;
import entity.Movie;
import listeners.ITaskCompleteListener;
import popularmovieconstants.Constants;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MovieDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MovieDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    static int currentOrientation;

    public Movie mCurrentMovie;
    public TextView mMovieTitleText;
    public ImageView mMoviePoster;
    public TextView mMovieOverview;
    public TextView mMovieVoteAverageText;
    public TextView mMovieDateReleasedfield;

   // public FetchMovieData mDataFetcher;
    public ListView mMovieTrailerList;
    public ListView mReviewList;

    public Button mReviewButton;
    public Button mTrailerButton;
    public Button mFavoriteButton;


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetailFragment newInstance(String param1, String param2) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        currentOrientation = getResources().getConfiguration().orientation;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       // mDataFetcher = new FetchMovieData(getActivity().getApplicationContext(),this);
        mReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(v);
            }
        });
        mTrailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(v);
            }
        });
        mFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFavoritePressed(v);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        mMovieTitleText = (TextView) mainView.findViewById(R.id.movie_title);
        mMoviePoster = (ImageView) mainView.findViewById(R.id.movie_poster_detail);
        mMovieOverview = (TextView) mainView.findViewById(R.id.movie_overview);
        mMovieVoteAverageText = (TextView) mainView.findViewById(R.id.movie_average);
        mMovieDateReleasedfield = (TextView) mainView.findViewById(R.id.movie_release_date_field);
        mMovieTrailerList = (ListView) mainView.findViewById(R.id.movie_trailer_list);
        mReviewList = (ListView) mainView.findViewById(R.id.movie_review_list);
        mReviewButton = (Button) mainView.findViewById(R.id.switch_to_review_button);
        mTrailerButton = (Button) mainView.findViewById(R.id.switch_to_trailer_button);
        mFavoriteButton = (Button) mainView.findViewById(R.id.mark_as_favorites_button);
        return mainView;
    }

    public void onButtonPressed(View v) {
        if (mListener != null) {
            mListener.onFragmentInteraction(v);
        }
    }
    public void onFavoritePressed(View v) {
        if (mListener != null) {
            mListener.onFragmentInteraction(v,mCurrentMovie);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(View v);
        public void onFragmentInteraction(View v,Movie m);
    }

    public void updateContent(int position) {
        Movie selectedMovie = Constants.mMovies.get(position);
        mCurrentMovie = selectedMovie;
        mMovieTitleText.setText(selectedMovie.getTitle());
        mMovieOverview.setText(selectedMovie.getOverview());
        mMovieVoteAverageText.setText(String.format(getString(R.string.movie_rating_date), selectedMovie.getVoteAverage()));
        mMovieDateReleasedfield.setText(selectedMovie.getReleaseDate().split("-")[0]);
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Landscape
            String movieposterurllarge = (this.getString(R.string.moviedb_poster_base_url).concat(this.getString(R.string.moviedb_size_w185)).concat(selectedMovie.getPath()));
            Picasso.with(getActivity().getApplicationContext()).load(movieposterurllarge).into(mMoviePoster);
        }
        else {
            // Portrait
            String movieposterurllarge = (this.getString(R.string.moviedb_poster_base_url).concat(this.getString(R.string.moviedb_size_w342)).concat(selectedMovie.getPath()));
            Picasso.with(getActivity().getApplicationContext()).load(movieposterurllarge).into(mMoviePoster);
        }
        ((MainActivity)getActivity()).retrieveReviewsAndTrailersForFragment(selectedMovie.getID());
    }
}
