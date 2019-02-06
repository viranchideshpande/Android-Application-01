/**
 * Assignment:Homework 5
 * Filename: track_details.java
 * Students: Dharak Shah,Viranchi Deshpande
 */
package com.example.dharak029.homework05;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class track_details extends AppCompatActivity {

    TextView txtName;
    TextView txtArtist;
    TextView txtURL;
    ListView listView;
    static ArrayList<MusicInfo> similarTracks = new ArrayList<MusicInfo>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_details);
        txtName = (TextView)findViewById(R.id.txtName);
        txtArtist = (TextView)findViewById(R.id.txtArtist);
        txtURL = (TextView)findViewById(R.id.txtURL);
        listView = (ListView)findViewById(R.id.listSimilarTrack);
        listView.setItemsCanFocus(true);

        MusicInfo musicInfo = (MusicInfo) getIntent().getSerializableExtra("musicInfoObject");

        txtName.setText(musicInfo.getName());
        txtArtist.setText(musicInfo.getArtist());
        txtURL.setText(musicInfo.getUrl());
        new GetImage().execute(musicInfo.getLargeImgURL());

        String url = "http://ws.audioscrobbler.com/2.0/?method=track.getsimilar&artist=";
        url = url+txtArtist.getText().toString()+"&track=";
        url = url+txtName.getText().toString()+"&api_key=d0fe053a4ab2514d0f232eecf22bd4fd&limit=10&format=json";
        new GetSearchResults(track_details.this).execute(url);


        txtURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(txtURL.getText().toString()));
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MusicInfo track = similarTracks.get(position);
                txtName.setText(track.getName());
                txtArtist.setText(track.getArtist());
                txtURL.setText(track.getUrl());
                new GetImage().execute(track.getLargeImgURL());
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


    public class GetImage extends AsyncTask<String,Void,Bitmap> {


        @Override
        protected Bitmap doInBackground(String... params) {
            try{
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("GET");
                Bitmap image = BitmapFactory.decodeStream(con.getInputStream());
                return image;
            }
            catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Bitmap s) {
            super.onPostExecute(s);
            if(s != null) {
                ImageView img = (ImageView) findViewById(R.id.imgLargeImg);
                img.setImageBitmap(s);
            }
        }
    }
}
