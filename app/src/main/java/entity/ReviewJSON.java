package entity;

import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;

/**
 * Created by Douglas on 8/16/2015.
 */
public class ReviewJSON {

    @SerializedName("id")
    public int id;

    @SerializedName("page")
    public int page;

    @SerializedName("reviews")
    public LinkedTreeMap result;

    @SerializedName("total_pages")
    public int totalpages;

    @SerializedName("total_results")
    public int totalresults;

    @SerializedName("url")
    public String url;

    public ReviewJSON(int id, int page, int totalpages, int totalresults, LinkedTreeMap objects, String url){
        this.id = id;
        this.page = page;
        this.result = objects;
        this.totalpages = totalpages;
        this.totalresults = totalresults;
        this.url = url;
    }


}
