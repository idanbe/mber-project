package com.example.administrator.game_4_in_a_row;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class About extends AppCompatActivity {

    private final String SETTING_KEY_SOUND = "SETTING_KEY_SOUND";
    private static final String SHARED_PREFERENCES_NAME = "ShardPreferences_setting";
    private final String ON = "on";
    private Button back;
    private Boolean SoundFlag = false ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        back = (Button)findViewById(R.id.button_about_back);

        // shared file
        SharedPreferences sharedpreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        if(sharedpreferences.getString(SETTING_KEY_SOUND, null) == null || sharedpreferences.getString(SETTING_KEY_SOUND, null).equals(ON) ){
            if(MainActivity.getMusic() != null)
                MainActivity.getMusic().start();
                SoundFlag = true ;
        }


        // back button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        if( !MainActivity.getMusic().isPlaying() && SoundFlag ){
            MainActivity.getMusic().start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if( MainActivity.getMusic().isPlaying()) {
            SoundFlag = true;
            MainActivity.getMusic().pause();
        }
        else {
            SoundFlag = false ;
        }
    }
}
