/**
 * Assignment:Homework 5
 * Filename: MusicSearchUtil.java
 * Students: Dharak Shah,Viranchi Deshpande
 */
package com.example.dharak029.homework05;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dharak029 on 10/9/2017.
 */

public class MusicSearchUtil {

    public static ArrayList<MusicInfo> parseMusicSearchQuery(String inputStream) throws JSONException {

        ArrayList<MusicInfo> musicInfoArrayList = new ArrayList<MusicInfo>();
        if(inputStream.contains("similartracks")){

            JSONObject jsonObject = new JSONObject(inputStream);
            JSONObject resultsObject = jsonObject.getJSONObject("similartracks");
            JSONArray searchArray = resultsObject.getJSONArray("track");

            for(int i=0;i<searchArray.length();i++){
                JSONObject obj = searchArray.getJSONObject(i);
                musicInfoArrayList.add(MusicInfo.createSimilarMusicObject(obj));
            }

        }
        else{
            JSONObject jsonObject = new JSONObject(inputStream);
            JSONObject resultsObject = jsonObject.getJSONObject("results");
            JSONObject trackmatchesObject = resultsObject.getJSONObject("trackmatches");
            JSONArray searchArray = trackmatchesObject.getJSONArray("track");


            for(int i=0;i<searchArray.length();i++){
                JSONObject obj = searchArray.getJSONObject(i);
                musicInfoArrayList.add(MusicInfo.createMusicObject(obj));
            }
        }
        return musicInfoArrayList;
    }

}
