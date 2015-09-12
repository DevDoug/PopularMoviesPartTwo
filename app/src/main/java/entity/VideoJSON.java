package entity;

import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Douglas on 8/16/2015.
 */
public class VideoJSON {

    @SerializedName("id")
    public int id;

    @SerializedName("page")
    public int page;

    @SerializedName("videos")
    public LinkedTreeMap videos;

    @SerializedName("total_pages")
    public int totalpages;

    @SerializedName("total_results")
    public int totalresults;

    @SerializedName("url")
    public String url;

    public VideoJSON(int id, int page, int totalpages, int totalresults, LinkedTreeMap objects, String url){
        this.id = id;
        this.page = page;
        this.videos = objects;
        this.totalpages = totalpages;
        this.totalresults = totalresults;
        this.url = url;
    }
}
