package com.example.administrator.game_4_in_a_row;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

//settings of game sound and vibration
public class Settings extends AppCompatActivity {

    private Button sound,vibriton,back;
    private final String SOUND_OFF="Sound Off";
    private final String SOUND_ON="Sound On";
    private final String Vibrtion_OFF="Vibration Off";
    private final String Vibrtion_ON="Vibration On";
    private final String SETTING_KEY_SOUND = "SETTING_KEY_SOUND";
    private final String SETTING_KEY_VIBRITON = "SETTING_KEY_VIBRITON";
    private static final String SHARED_PREFERENCES_NAME = "ShardPreferences_setting";
    private final String ON = "on";
    private final String OFF = "off";
    private SharedPreferences sharedpreferences ;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // object
        sound = (Button) findViewById(R.id.button_sound);
        vibriton = (Button) findViewById(R.id.button_Vibration);
        back = (Button)findViewById(R.id.button_backSittings);

        // shared file
        sharedpreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();


        // check setting sound from shared file
        if(sharedpreferences.getString(SETTING_KEY_SOUND, null) == null){
            sound.setText(SOUND_ON);
            editor.putString(SETTING_KEY_SOUND, ON);
            if(!MainActivity.getMusic().isPlaying())
                MainActivity.getMusic().start();
            editor.commit();
        }
        else if(sharedpreferences.getString(SETTING_KEY_SOUND, null).equals(OFF)){
            sound.setText(SOUND_OFF);
            if(MainActivity.getMusic().isPlaying())
                MainActivity.getMusic().pause();
        }
        else {
            if(MainActivity.getMusic() != null)
                MainActivity.getMusic().start();
        }


        // check setting vibriton from shared file
        if(sharedpreferences.getString(SETTING_KEY_VIBRITON , null) == null){
            vibriton.setText(Vibrtion_ON);
            editor.putString(SETTING_KEY_VIBRITON, ON);
            editor.commit();
        }
        else if(sharedpreferences.getString(SETTING_KEY_VIBRITON , null).equals(OFF)){
            vibriton.setText(Vibrtion_OFF);
        }

        //sound button on/off
        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = sharedpreferences.getString(SETTING_KEY_SOUND , null);
                if( s.equals(ON) ) {
                    sound.setText(SOUND_OFF);
                    editor.putString(SETTING_KEY_SOUND, OFF);
                    if(MainActivity.getMusic().isPlaying())
                        MainActivity.getMusic().pause();
                }
                else
                {
                    sound.setText(SOUND_ON);
                    editor.putString(SETTING_KEY_SOUND, ON);
                    if(!MainActivity.getMusic().isPlaying())
                        MainActivity.getMusic().start();
                }
                editor.commit();
            }
        });

        //vibration button on/off
        vibriton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = sharedpreferences.getString(SETTING_KEY_VIBRITON , null);
                if( s.equals(ON) ) {
                    vibriton.setText(Vibrtion_OFF);
                    editor.putString(SETTING_KEY_VIBRITON , OFF);
                }
                else
                {
                    vibriton.setText(Vibrtion_ON);
                    editor.putString(SETTING_KEY_VIBRITON, ON);
                }
                editor.commit();
            }
        });



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
        if( !MainActivity.getMusic().isPlaying() && sound.getText().toString().equals(SOUND_ON) ){
            MainActivity.getMusic().start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if( MainActivity.getMusic().isPlaying())
            MainActivity.getMusic().pause();
    }

}
