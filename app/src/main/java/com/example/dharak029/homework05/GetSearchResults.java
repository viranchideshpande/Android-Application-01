/**
 * Assignment:Homework 5
 * Filename: GetSearchResults.java
 * Students: Dharak Shah,Viranchi Deshpande
 */
package com.example.dharak029.homework05;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;



public class GetSearchResults extends AsyncTask<String,Void,ArrayList<MusicInfo>>{

    MainActivity activity = null;
    track_details track_details_activity  = null;
    public GetSearchResults(MainActivity activity) {
        this.activity = activity;
    }

    public  GetSearchResults(track_details track_details_activity){
        this.track_details_activity = track_details_activity;
    }

    @Override
    protected ArrayList<MusicInfo> doInBackground(String... params) {

        BufferedReader br = null;
        URL url = null;

        try {
            url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();
                while (line != null) {
                    sb.append(line);
                    line = br.readLine();
                }

                ArrayList<MusicInfo> musicInfoArrayList = MusicSearchUtil.parseMusicSearchQuery(sb.toString());
                return musicInfoArrayList;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<MusicInfo> musicInfoArrayList) {

        if(activity!=null){
            activity.setData(musicInfoArrayList);
            Intent intent = new Intent(activity,search_result.class);
            activity.startActivity(intent);
        }
        if(track_details_activity !=null && musicInfoArrayList!=null){
            track_details_activity.similarTracks = musicInfoArrayList;
            ListView listView = (ListView)track_details_activity.findViewById(R.id.listSimilarTrack);
            ResultAdapter resultAdapter = new ResultAdapter(track_details_activity,R.layout.music_item,musicInfoArrayList);
            listView.setAdapter(resultAdapter);
        }

        super.onPostExecute(musicInfoArrayList);
    }
}
