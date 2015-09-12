package adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.douglas.popularmovies.R;
import java.util.ArrayList;
import entity.Movie;
import entity.Trailer;

/**
 * Created by Douglas on 8/25/2015.
 */
public class MovieTrailerAdapter extends ArrayAdapter {

    private Context mContext;
    private ArrayList<Trailer> mItems;

    public MovieTrailerAdapter(Context context, ArrayList objects) {
        super(context, R.layout.movie_trailer_list_item, objects);
        this.mContext = context;
        this.mItems = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //if the view is null than inflate it otherwise just fill the list with
        if (convertView == null) {
            //inflate the layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.movie_trailer_list_item, parent, false);
        }

        TextView trailername = (TextView) convertView.findViewById(R.id.trailer_name);
        trailername.setText(mItems.get(position).getName());
        trailername.setTag(mItems.get(position).getID());

        return convertView;
    }
}
