package entity;

import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Douglas on 8/16/2015.
 */
public class MovieJSON {

    @SerializedName("id")
    public String movieid;

    @SerializedName("page")
    public int page;

    @SerializedName("results")
    public ArrayList<LinkedTreeMap> result;

    @SerializedName("total_pages")
    public int totalpages;

    @SerializedName("total_results")
    public int totalresults;

    public MovieJSON(String id,int page, int totalpages, int totalresults, ArrayList<LinkedTreeMap> objects) {
        this.movieid = id;
        this.page = page;
        this.result = objects;
        this.totalpages = totalpages;
        this.totalresults = totalresults;
    }
}
