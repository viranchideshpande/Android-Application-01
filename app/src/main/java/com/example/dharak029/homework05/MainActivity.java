/**
 * Assignment:Homework 5
 * Filename: MainActivity.java
 * Students: Dharak Shah,Viranchi Deshpande
 */
package com.example.dharak029.homework05;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editSearch;
    Button btnSearch;
    ListView favoriteListView;
    static ArrayList<MusicInfo> musicInfoArrayList;
    static ArrayList<MusicInfo> favoriteList;
    ListView lstFavorites;
    static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSearch = (Button)findViewById(R.id.btnSearch);
        editSearch = (EditText) findViewById(R.id.editSearch);
        favoriteListView = (ListView)findViewById(R.id.lstFavorites);
        lstFavorites = (ListView)findViewById(R.id.lstFavorites);
        favoriteList = new ArrayList<MusicInfo>();

        context = getApplicationContext();

        if(retrieveFromPreference()==null || retrieveFromPreference().size()!=0){
            favoriteList = retrieveFromPreference();
        }
        else{
            favoriteList = new ArrayList<MusicInfo>();
        }

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://ws.audioscrobbler.com/2.0/?method=track.search&track=";
                url = url+editSearch.getText()+"&api_key=d0fe053a4ab2514d0f232eecf22bd4fd&limit=20&format=json";
                new GetSearchResults(MainActivity.this).execute(url);
            }
        });

        if(retrieveFromPreference()!=null && retrieveFromPreference().size()!=0){
            ResultAdapter resultAdapter = new ResultAdapter(this,R.layout.music_item,retrieveFromPreference());
            lstFavorites.setAdapter(resultAdapter);
            resultAdapter.setNotifyOnChange(true);
            lstFavorites.setItemsCanFocus(true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case R.id.action_quit:
                finish();
                moveTaskToBack(true);
                finish();
        }

        return super.onOptionsItemSelected(item);
    }


    public static ArrayList<MusicInfo>  retrieveFromPreference(){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("favoriteList", null);
        Type type = new TypeToken<ArrayList<MusicInfo>>() {}.getType();
        ArrayList<MusicInfo> favoriteList = gson.fromJson(json, type);
        return favoriteList;
    }
    public static void addToPreference(){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Log.d("demo",""+favoriteList.size());
        Gson gson = new Gson();
        String json = gson.toJson(favoriteList);
        editor.putString("favoriteList", json);
        editor.commit();
    }
    public static void setData(ArrayList<MusicInfo> list){
        musicInfoArrayList = list;
    }
}
