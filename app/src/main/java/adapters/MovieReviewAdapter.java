package adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.douglas.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import entity.Movie;
import entity.Review;

/**
 * Created by Douglas on 8/30/2015.
 */
public class MovieReviewAdapter extends ArrayAdapter {
    private Context mContext;
    private ArrayList<Review> mItems;

    public MovieReviewAdapter(Context context, ArrayList<Review> objects) {
        super(context, R.layout.movie_review_list_item, objects);
        this.mContext = context;
        this.mItems = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //if the view is null than inflate it otherwise just fill the list with
        if (convertView == null) {
            //inflate the layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.movie_review_list_item, parent, false);
        }

        TextView trailername = (TextView) convertView.findViewById(R.id.movie_review_name);
        trailername.setText(mItems.get(position).getContent());

        return convertView;
    }
}
