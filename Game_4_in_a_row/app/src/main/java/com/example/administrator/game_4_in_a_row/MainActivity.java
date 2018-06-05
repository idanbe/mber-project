package com.example.administrator.game_4_in_a_row;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.example.lib.FirebaseConfigWrapper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;


public class MainActivity extends AppCompatActivity {

    private Intent intent;
    private Button single_button,two_button,setting_button,game_history_button,about_button,exit_button;
    static final String onePlayer_key ="player1";
    static final String twoPlayer_key ="player2";
    static final String ONE_PLAYER ="one_player";
    static final String TWO_PLAYER ="two_player";
    private static MediaPlayer music;
    private static Boolean SoundFlag;
    private static Boolean VibritonFlag;
    private final String SETTING_KEY_SOUND = "SETTING_KEY_SOUND";
    private final String SETTING_KEY_VIBRITON = "SETTING_KEY_VIBRITON";
    private final String SHARED_PREFERENCES_NAME = "ShardPreferences_setting";
    private final String ON = "on";
    private final int REQUEST_MICROPHONE =1;
    private SharedPreferences sharedpreferences ;
    SharedPreferences.Editor editor;
    private  Properties prop;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "On Create");

        FirebaseMessaging.getInstance().setAutoInitEnabled(true);

    //~~~~~~~~~~~~~~~~~~~~~~Check-Permission~~~~~~~~~~~~~~~~~~~~~~~~~~~

        checkUserPermission();

        checkGooglePlayService();

    //~~~~~~~~~~~~~~~~~~~~~~Remote-config~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        prop = new Properties();

        // Get Remote Config instance.
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        mFirebaseRemoteConfig.setConfigSettings(new FirebaseRemoteConfigSettings.Builder()
        .setDeveloperModeEnabled(true)
        .build());

        final Task<Void> fetch = mFirebaseRemoteConfig.fetch(0);

        fetch.addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mFirebaseRemoteConfig.activateFetched();
                updateProperties();
            }
        });

       //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



        //  loop mus
       if(music == null || !music.isPlaying()){
            music = MediaPlayer.create(MainActivity.this, R.raw.background_sound);
            music.setLooping(true);
        }

        // status of setting
        checkSetting();

        // if music on
        if(SoundFlag && !music.isPlaying()) {
            music.start();
        }

        DAL dal = new DAL(this);


        single_button = (Button)findViewById(R.id.button_single);
        two_button = (Button)findViewById(R.id.button_two);
        setting_button = (Button)findViewById(R.id.button_setting);
        game_history_button = (Button)findViewById(R.id.button_statistics);
        exit_button = (Button)findViewById(R.id.button_Exit);
        about_button = (Button)findViewById(R.id.button_about);


        // single player
        single_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), Names.class);
                intent.putExtra(onePlayer_key,ONE_PLAYER.toString());
                startActivity(intent);
            }
        });

        // Two players
        two_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), Names.class);
                intent.putExtra(twoPlayer_key,TWO_PLAYER.toString());
                startActivity(intent);
            }
        });

        // setting
        setting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), Settings.class);
                startActivity(intent);
            }
        });


        // game history
        game_history_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), Game_history.class);
                startActivity(intent);
            }
        });


        // About
        about_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), About.class);
                startActivity(intent);
            }
        });



        // exit
        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(1);
            }
        });






    }

    private void checkGooglePlayService() {
        // Getting status
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        // Showing status
        if (status == ConnectionResult.SUCCESS) {
            // Log.d(TAG, "onCreate: Google Play Services are available ");
        }
        else{
            Log.d(TAG, "onCreate: Google Play Services are not available");
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
        }
    }

    private void checkUserPermission() {

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
        {
            Log.d(TAG, "checkUserPermission: Ask for RECORD_AUDIO Permission");
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    REQUEST_MICROPHONE);
        }
    }

    private void updateProperties() {
        String config_url = mFirebaseRemoteConfig.getString("config_url");
        String hash_url = mFirebaseRemoteConfig.getString("hash_url");
        String log_url = mFirebaseRemoteConfig.getString("log_url");
        String user_data_url = mFirebaseRemoteConfig.getString("user_data_url");

        // set the properties value
        prop.setProperty("config_url", config_url);
        prop.setProperty("hash_url", hash_url);
        prop.setProperty("log_url", log_url);
        prop.setProperty("user_data_url", user_data_url);

        Log.d(TAG,"Remote config - Properties values:" + prop.toString());
        FirebaseConfigWrapper.receiveConfig(prop);

    }


    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkSetting();
        if( !music.isPlaying() && SoundFlag ){
            music.start();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();

    }


    @Override
    protected void onPause() {
        super.onPause();
        if( music.isPlaying())
            music.pause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);


            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    // music
    public static MediaPlayer getMusic(){
        return music;
    }


    // check from sharedP the sound and vibrator
    public void checkSetting(){
        sharedpreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        if(sharedpreferences.getString(SETTING_KEY_SOUND, null) == null || sharedpreferences.getString(SETTING_KEY_SOUND, null).equals(ON) ){
            SoundFlag = true;
        }
        else{
            SoundFlag = false;
        }
        if(sharedpreferences.getString(SETTING_KEY_VIBRITON , null) == null || sharedpreferences.getString(SETTING_KEY_VIBRITON , null).equals(ON)){
            VibritonFlag = true ;
        }
        else {
            VibritonFlag = false;
        }
    }
}
