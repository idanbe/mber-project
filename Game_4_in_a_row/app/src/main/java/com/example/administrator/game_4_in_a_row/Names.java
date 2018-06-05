package com.example.administrator.game_4_in_a_row;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Names extends AppCompatActivity {

    private Intent intent;
    private Button back_button,next_button;
    private EditText p1,p2;
    static final String p1_key ="key1";
    static final String p2_key ="key2";
    static final String onePlayer_key ="player1";
    static final String twoPlayer_key ="player2";
    static final String Game_Type_key ="gameType_key";
    private static final String TWO_PLAYER = "two_player" ;
    private Bundle bundle;
    private String non_player_name ="";
    private View view ;
    private AlertDialog alertDialog;
    static final String COMPUTER ="Computer";
    private final String SETTING_KEY_SOUND = "SETTING_KEY_SOUND";
    private final String SHARED_PREFERENCES_NAME = "ShardPreferences_setting";
    private final String ON = "on";
    private final String NAME_PLAYERS = "Names of players:";
    private final String VS = "\n VS \n";
    private final String OK = "OK";
    private final String CANCEL = "Cancel";
    private final String RESET_NAME_PLAYERS = "";
    private Boolean SoundFlag = false ;


    private void checkSound(){
        SharedPreferences sharedpreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        if(sharedpreferences.getString(SETTING_KEY_SOUND, null) == null || sharedpreferences.getString(SETTING_KEY_SOUND, null).equals(ON) ){
            if(MainActivity.getMusic() != null) {
                MainActivity.getMusic().start();
                SoundFlag = true;
            }
        }
        else {
            SoundFlag = false ;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_names);

        bundle = getIntent().getExtras();


        SharedPreferences sharedpreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        if(sharedpreferences.getString(SETTING_KEY_SOUND, null) == null || sharedpreferences.getString(SETTING_KEY_SOUND, null).equals(ON) ){
            if(MainActivity.getMusic() != null) {
                MainActivity.getMusic().start();
                SoundFlag = true;
            }
        }




        // get objects
        back_button = (Button)findViewById(R.id.button_back);
        next_button = (Button)findViewById(R.id.button_next);
        p1 = (EditText)findViewById(R.id.player1_textv);
        p2 = (EditText)findViewById(R.id.player2_text);

        p1.setText(non_player_name);
        p2.setText(non_player_name);

        //get game type
        if (bundle != null)
        {
            if(!(bundle.getString(onePlayer_key)==null))
            {
                non_player_name =bundle.getString(onePlayer_key).toString();
                p2.setVisibility(View.INVISIBLE);
                p2.setText(COMPUTER);
            }
            if(!(bundle.getString(twoPlayer_key)==null))
            {
                non_player_name =bundle.getString(twoPlayer_key).toString();
            }
        }

        // next button
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view = v ;
                alertDialog = new AlertDialog.Builder(Names.this).create();
                // restart string
                String s  = "";

                // set players from users
                alertDialog.setTitle(NAME_PLAYERS);
                if (p1.getText().toString().isEmpty() || p1.getText().toString().equals(COMPUTER)) {
                    s += onePlayer_key;
                }
                else {
                    s += p1.getText().toString();
                }
                s += VS;
                if( !non_player_name.equals(TWO_PLAYER)){
                    s += COMPUTER;
                }
                else if ( (p2.getText().toString().isEmpty() || (p2.getText().toString().equals(COMPUTER) && non_player_name.equals(TWO_PLAYER)) )){
                        s += twoPlayer_key;
                }

                else {
                    s += p2.getText().toString();
                }
                // VS Massage
                if(p1.getText().toString().equals(p2.getText().toString()) && non_player_name.equals(TWO_PLAYER)){
                    s = onePlayer_key + VS + twoPlayer_key ;
                }

                alertDialog.setMessage(s);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, OK,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                                // go to game
                                intent = new Intent(view.getContext(), Game.class);
                                intent.putExtra(p1_key, p1.getText().toString());
                                intent.putExtra(p2_key, p2.getText().toString());
                                intent.putExtra(Game_Type_key, non_player_name.toString());

                                // restart edit test
                                p1.setText(RESET_NAME_PLAYERS);
                                p2.setText(RESET_NAME_PLAYERS);

                                // go to game
                                startActivity(intent);
                            }
                        });
                //  cancel button
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, CANCEL,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();



            }
        });


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // restart name player
                p1.setText(RESET_NAME_PLAYERS);
                p2.setText(RESET_NAME_PLAYERS);
                onBackPressed();
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        checkSound();
        if( !MainActivity.getMusic().isPlaying() && SoundFlag ){
            MainActivity.getMusic().start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if( MainActivity.getMusic().isPlaying())
            MainActivity.getMusic().pause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_names, menu);
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
            intent = new Intent(Names.this, Settings.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
