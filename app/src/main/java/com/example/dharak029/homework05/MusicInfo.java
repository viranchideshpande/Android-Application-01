/**
 * Assignment:Homework 5
 * Filename: MusicInfo.java
 * Students: Dharak Shah,Viranchi Deshpande
 */
package com.example.dharak029.homework05;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by dharak029 on 10/9/2017.
 */

public class MusicInfo implements Serializable {

    String smallImgURL,largeImgURL,name,artist,url;
    boolean checked;


    public static MusicInfo createMusicObject(JSONObject js) throws JSONException{

        MusicInfo musicInfo = new MusicInfo();

        musicInfo.setName(js.getString("name"));
        musicInfo.setArtist(js.getString("artist"));
        musicInfo.setUrl(js.getString("url"));
        musicInfo.setChecked(false);

        JSONArray imageArray = js.getJSONArray("image");

        JSONObject smalImageObj = imageArray.getJSONObject(0);
        musicInfo.setSmallImgURL(smalImageObj.getString("#text"));
        JSONObject largeImageObj = imageArray.getJSONObject(2);
        musicInfo.setLargeImgURL(largeImageObj.getString("#text"));
        return musicInfo;
    }

    public static MusicInfo createSimilarMusicObject(JSONObject js) throws JSONException{

        MusicInfo musicInfo = new MusicInfo();

        musicInfo.setName(js.getString("name"));
        musicInfo.setArtist(js.getJSONObject("artist").getString("name"));
        musicInfo.setUrl(js.getString("url"));
        musicInfo.setChecked(false);

        JSONArray imageArray = js.getJSONArray("image");

        JSONObject smalImageObj = imageArray.getJSONObject(0);
        musicInfo.setSmallImgURL(smalImageObj.getString("#text"));
        JSONObject largeImageObj = imageArray.getJSONObject(2);
        musicInfo.setLargeImgURL(largeImageObj.getString("#text"));
        return musicInfo;
    }

    public String getSmallImgURL() {
        return smallImgURL;
    }

    public void setSmallImgURL(String smallImgURL) {
        this.smallImgURL = smallImgURL;
    }

    public String getLargeImgURL() {
        return largeImgURL;
    }

    public void setLargeImgURL(String largeImgURL) {
        this.largeImgURL = largeImgURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }


}
