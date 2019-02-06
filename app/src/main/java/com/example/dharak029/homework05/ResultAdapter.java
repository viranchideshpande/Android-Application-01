/**
 * Assignment:Homework 5
 * Filename: ResultAdapter.java
 * Students: Dharak Shah,Viranchi Deshpande
 */
package com.example.dharak029.homework05;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by dharak029 on 10/9/2017.
 */

public class ResultAdapter extends ArrayAdapter<MusicInfo> {

    ArrayList<MusicInfo> musicInfoArrayList;
    Context context;
    int resource;
    MusicInfo musicInfo;

    public ResultAdapter(Context context,int resource,ArrayList<MusicInfo> musicInfoArrayList){
        super(context,resource,musicInfoArrayList);
        this.context = context;
        this.resource = resource;
        this.musicInfoArrayList = musicInfoArrayList;
    }

    @Override
    public int getViewTypeCount() {

        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(resource,parent,false);
        }

        TextView txtSongName = (TextView)convertView.findViewById(R.id.txtSongName);
        TextView txtArtistName =  (TextView)convertView.findViewById(R.id.txtArtistName);
        final ImageButton imageButton = (ImageButton)convertView.findViewById(R.id.favoriteButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(imageButton.getTag()==null ) {
                        imageButton.setImageResource(android.R.drawable.btn_star_big_on);
                        imageButton.setTag("off");
                        MainActivity.favoriteList.add(musicInfoArrayList.get(position));
                        MainActivity.addToPreference();
                        Toast.makeText(context, "Track added to favorites."+musicInfoArrayList.get(position).getName(), Toast.LENGTH_SHORT).show();
                    }
                    else{
                        imageButton.setImageResource(android.R.drawable.btn_star_big_off);
                        imageButton.setTag(null);
                        MainActivity.favoriteList.remove(position);
                        MainActivity.addToPreference();
                        if(context.toString().contains("MainActivity") && musicInfo != null) {
                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                        }
                        Toast.makeText(context, "Track removed from favorites."+musicInfoArrayList.get(position).getName(), Toast.LENGTH_SHORT).show();
                    }
            }
        });

        musicInfo = musicInfoArrayList.get(position);
        txtSongName.setText(musicInfo.getName());
        txtArtistName.setText(musicInfo.getArtist());
        if(musicInfo.getSmallImgURL()!=null){
            new GetImage(convertView).execute(musicInfo.getSmallImgURL());
        }
        if(context.toString().contains("MainActivity") && musicInfo != null) {
            imageButton.setImageResource(android.R.drawable.btn_star_big_on);
            imageButton.setTag("off");
        }
        else if(MainActivity.favoriteList != null){
            for(int i=0;i<MainActivity.favoriteList.size();i++) {
                if (MainActivity.favoriteList.get(i).getName().equals(musicInfoArrayList.get(position).getName())) {
                    imageButton.setImageResource(android.R.drawable.btn_star_big_on);
                    imageButton.setTag("off");
                }
            }
        }

        return convertView;
    }


    public class GetImage extends AsyncTask<String,Void,Bitmap> {

        View convertview;

        public GetImage(View convertview){
            this.convertview = convertview;
        }

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
                ImageView img = (ImageView) convertview.findViewById(R.id.imgSong);
                img.setImageBitmap(s);
            }
        }
    }
}






