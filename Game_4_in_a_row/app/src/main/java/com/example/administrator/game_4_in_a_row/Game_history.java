package com.example.administrator.game_4_in_a_row;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class Game_history extends AppCompatActivity {

    private Intent intent;
    private DAL dal;
    private TableLayout HistoryTable ;
    private TableRow tableRow;
    private ArrayList<Row> rowArrayList ;
    private Button resetTable,back ;
    private final String SETTING_KEY_SOUND = "SETTING_KEY_SOUND";
    private final String SHARED_PREFERENCES_NAME = "ShardPreferences_setting";
    private Boolean SoundFlag = false ;
    private final String ON = "on";
    private static final int MAX_RESULT = 10;
    private static final String PERCENT = "%";
    private static final String REMOVE_TITTLE_MASSAGE = "Remove All Data From Table :";
    private static final String REMOVE_BODY_MASSAGE = "All Data will be deleted\n" + "Are you sure ?";
    private static final String OK = "OK";
    private static final String CANCEL = "Cancel";
    private static final int TABLE_FINAL_ROWS_1 = 0;
    private static final int TABLE_FINAL_ROWS_2 = 1;



    //object like struct
    static class RowHolder {
        TextView name;
        TextView win;
        TextView loss;
        TextView standoff;
        TextView percent_win;
    }

    // add row
    public void addToHistoryTable(String name , int win , int loss , int draws , double percent ){

        RowHolder rowHolder = new RowHolder();
        tableRow = new TableRow(this);
        rowHolder.name = new TextView(this);
        rowHolder.win = new TextView(this);
        rowHolder.loss = new TextView(this);
        rowHolder.standoff = new TextView(this);
        rowHolder.percent_win = new TextView(this);

        // name col
        rowHolder.name.setText(name);
        rowHolder.name.setGravity(Gravity.LEFT);

        // win col
        rowHolder.win.setText(Integer.toString(win));
        rowHolder.win.setGravity(Gravity.CENTER);

        // loss col
        rowHolder.loss.setText(Integer.toString(loss));
        rowHolder.loss.setGravity(Gravity.CENTER);

        // standoff col
        rowHolder.standoff.setText(Integer.toString(draws));
        rowHolder.standoff.setGravity(Gravity.CENTER);

        // percent_win col
        rowHolder.percent_win.setText(percent + PERCENT);
        rowHolder.percent_win.setGravity(Gravity.RIGHT);

        // add to row
        tableRow.addView(rowHolder.name);
        tableRow.addView(rowHolder.win);
        tableRow.addView(rowHolder.loss);
        tableRow.addView(rowHolder.standoff);
        tableRow.addView(rowHolder.percent_win);
        // Color yellow
        tableRow.setBackgroundColor(Color.YELLOW);

        // add row to history Table
        HistoryTable.addView(tableRow);
    }

    // update
    public void upDateHistoryTable(String name , boolean ifwin , boolean ifStendOff) {
        dal.upDateWinOrLoss(name, ifwin, ifStendOff);
        rowArrayList = dal.getDb();
    }

    public void removeRowFromHistory(String name){
        dal.removeRow(name);
        rowArrayList = dal.getDb();
    }

    /*// TODO : not remove !!
    public void printArray(){
        for(int i = 0 ; i < rowArrayList.size() ; i++){
            Row row = rowArrayList.get(i);
            System.out.println(row.getName() + " , " + row.getWin() + " , " + row.getLoss() + " , " + row.getDraws() + " , " + row.getPercent_Win() + "%" );
        }
    }*/

    public int sortArrayList(){
        int indexOfMakValue = 0 ;
        double maxPercent = 0 ;
        int maxNumberOfWin = 0;
        for(int i = 0 ; i < rowArrayList.size() ; i++){
            if(rowArrayList.get(i).getPercent_Win() > maxPercent){
                indexOfMakValue = i ;
                maxPercent = rowArrayList.get(i).getPercent_Win() ;
                maxNumberOfWin = rowArrayList.get(i).getWin();
            }
            else if(rowArrayList.get(i).getPercent_Win() == maxPercent && rowArrayList.get(i).getWin() > maxNumberOfWin){
                indexOfMakValue = i;
            }
        }
        return indexOfMakValue;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_history);

        dal = new DAL(this);

        resetTable = (Button)findViewById(R.id.resetData);
        back = (Button)findViewById(R.id.button_backHistory);

        // create Table layout
        HistoryTable = (TableLayout)findViewById(R.id.history_table);
        HistoryTable.setStretchAllColumns(true);

        SharedPreferences sharedpreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        if(sharedpreferences.getString(SETTING_KEY_SOUND, null) == null || sharedpreferences.getString(SETTING_KEY_SOUND, null).equals(ON) ){
           if(MainActivity.getMusic() != null && !MainActivity.getMusic().isPlaying())
            MainActivity.getMusic().start();
            SoundFlag = true ;
        }


        // DB to array list
        rowArrayList = dal.getDb();

        // print to history table
        int j = 0 ;
        while ( !rowArrayList.isEmpty() && j < MAX_RESULT){
            int i = sortArrayList();
            Row row = rowArrayList.get(i) ;
            addToHistoryTable(row.getName(), row.getWin(), row.getLoss(), row.getDraws(), row.getPercent_Win());
            rowArrayList.remove(i);
            j++ ;
        }


        // back button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }}
        );

        // rest table button
        resetTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dal.removeAll();
                AlertDialog alertDialog = new AlertDialog.Builder(Game_history.this).create();
                alertDialog.setTitle(REMOVE_TITTLE_MASSAGE);
                alertDialog.setMessage(REMOVE_BODY_MASSAGE);
                // ok button
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, OK,
                        new DialogInterface.OnClickListener() {
                            // remove all result rows
                            public void onClick(DialogInterface dialog, int which) {
                                // final rows (0, 1 in Table layout)
                                View view1 = HistoryTable.getChildAt(TABLE_FINAL_ROWS_1);
                                View view2 = HistoryTable.getChildAt(TABLE_FINAL_ROWS_2);
                                HistoryTable.removeAllViews();
                                HistoryTable.addView(view1);
                                HistoryTable.addView(view2);
                                dialog.dismiss();
                            }
                        });
                // cancel button
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
