/**
 * Assignment:Homework 5
 * Filename: search_result.java
 * Students: Dharak Shah,Viranchi Deshpande
 */
package com.example.dharak029.homework05;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class search_result extends AppCompatActivity {

    ArrayList<MusicInfo> musicInfoArrayList;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        musicInfoArrayList = MainActivity.musicInfoArrayList;
        listView = (ListView)findViewById(R.id.listResult);

        ResultAdapter resultAdapter = new ResultAdapter(this,R.layout.music_item,musicInfoArrayList);
        listView.setAdapter(resultAdapter);
        listView.setItemsCanFocus(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(search_result.this,track_details.class);
                intent.putExtra("musicInfoObject",musicInfoArrayList.get(position));
                startActivity(intent);
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_home)
        {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        else{
            finish();
            moveTaskToBack(true);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
