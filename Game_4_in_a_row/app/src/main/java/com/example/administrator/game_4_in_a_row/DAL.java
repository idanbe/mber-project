package com.example.administrator.game_4_in_a_row;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * Created by AviramAlkobi on 14/02/2016.
 */



public class DAL {


    private SQLiteOpenHelper helper;
    private final int INIT = 0;
    private final String PERCENT_FORMAT = "#.##";
    private final String ERROR = "error";
    private final int PERCENT_100 = 100;

    // object like struct
    private static class WinnersHolder {
        int win;
        int loss;
        int standoff;
    }

    public DAL(Context context){
        helper = new DBHelper(context);
    }

    // add row to DB
    public void addUser(String name){
        SQLiteDatabase db = helper.getWritableDatabase();

        if ( ! thereIsRow(name))
        {
            //values to save
            ContentValues values = new ContentValues();

            // add to table
            values.put(Tables.winnersTable.NAME, name);
            values.put(Tables.winnersTable.WINS, INIT);
            values.put(Tables.winnersTable.LOSSES, INIT);
            values.put(Tables.winnersTable.STAND_OFF, INIT);
            values.put(Tables.winnersTable.PERCENT_OF_WINS , INIT);

            //save the values
            db.insert(Tables.winnersTable.TABLE_NAME, null, values);
            db.close();
        }
        else
        {
            /* record not exist */
        }
    }


    // upDate DB
    public void upDateWinOrLoss(String name , Boolean thereIsWin , Boolean standoff){
        SQLiteDatabase db = helper.getWritableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();

        // Holder for 2 integer
        WinnersHolder info  = getPrevResult(name);

        // standoff
        if(standoff){
            info.standoff++;
            values.put(Tables.winnersTable.STAND_OFF , info.standoff );
        }

        // win or loss
        else{
            // if player win
            if (thereIsWin) {
                info.win++;
                values.put(Tables.winnersTable.WINS, info.win);
            }
            // if player loss
            else {
                info.loss++;
                values.put(Tables.winnersTable.LOSSES , info.loss);
            }
        }
        double avg = 0;
        double temp = (info.loss + info.win + info.standoff);
        avg = (info.win / temp) * PERCENT_100 ;

        // double format
        DecimalFormat twoDForm = new DecimalFormat(PERCENT_FORMAT);
        values.put(Tables.winnersTable.PERCENT_OF_WINS , Double.valueOf(twoDForm.format(avg)) );

        // Which row to update, based on the ID
        String selection = Tables.winnersTable.NAME + "='" + name + "'" ;

        int count = db.update(Tables.winnersTable.TABLE_NAME, values, selection, null);
    }


    // get number of wins , loss , standoff according to user name
    private WinnersHolder getPrevResult(String name){

        WinnersHolder info = new WinnersHolder() ;

        //get cursor on start row
        Cursor c = getRow(name);

        if (c != null) {
            while (c.moveToNext()) {

                //get column index
                int lossColumnIndex = c.getColumnIndex(Tables.winnersTable.LOSSES);
                int winColumnIndex = c.getColumnIndex(Tables.winnersTable.WINS);
                int StandOffColumnIndex = c.getColumnIndex(Tables.winnersTable.STAND_OFF);

                try{
                    info.win = c.getInt(winColumnIndex);
                    info.loss = c.getInt(lossColumnIndex);
                    info.standoff = c.getInt(StandOffColumnIndex);
                }
                catch (Exception e){
                }
            }
        }
        return info;
    }

    // if there is row with this complexity and level
    private Boolean thereIsRow(String name){
        Cursor c = getRow(name);
        if(c.moveToNext()){
            return true;
        }
        return false;
    }

    // get row according to complexity and level
    private Cursor getRow(String name){
        SQLiteDatabase db = helper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + Tables.winnersTable.TABLE_NAME + " WHERE "
                + Tables.winnersTable.NAME + "='" + name + "'"  , null);

        return c;
    }

    // remove all rows
    public  void removeAll(){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(Tables.winnersTable.TABLE_NAME, null, null);
    }

    // remove one row
    public  void removeRow(String name){
        SQLiteDatabase db = helper.getWritableDatabase();

        // search the correct row
        String selection = Tables.winnersTable.NAME + "='" + name+"'" ;

        // remove from DB
        db.delete(Tables.winnersTable.TABLE_NAME, selection, null);
    }


    private Cursor getAllDBCursor(){
        SQLiteDatabase db = helper.getWritableDatabase();
        // get all DB
        Cursor c = db.rawQuery("SELECT * FROM " + Tables.winnersTable.TABLE_NAME, null);

        return c;
    }

    // write all DB in ArrayList
    public ArrayList<Row> getDb(){

        ArrayList arrayListRow = new ArrayList();

        //get cursor
        Cursor c = getAllDBCursor();

        if (c != null) {
            while (c.moveToNext()) {
                //get column index
                Row row = new Row();
                int entryTimeColumnIndex = c.getColumnIndex(Tables.winnersTable.NAME);

                //get entry
                try{
                    String entryTime = c.getString(entryTimeColumnIndex);
                    row.setName(entryTime);
                }
                //save in array
                catch (Exception e){
                    row.setName(ERROR);
                }

                entryTimeColumnIndex = c.getColumnIndex(Tables.winnersTable.WINS);

                //get entry
                try{
                    String entryTime = c.getString(entryTimeColumnIndex);
                    row.setWin(Integer.parseInt(entryTime));
                }
                //save in array
                catch (Exception e){
                    row.setWin(INIT);
                }

                entryTimeColumnIndex = c.getColumnIndex(Tables.winnersTable.LOSSES);

                //get entry
                try{
                    String entryTime = c.getString(entryTimeColumnIndex);
                    row.setLoss(Integer.parseInt(entryTime));
                }
                //save in array
                catch (Exception e){
                    row.setLoss(INIT);
                }
                entryTimeColumnIndex = c.getColumnIndex(Tables.winnersTable.STAND_OFF);

                //get entry
                try{
                    String entryTime = c.getString(entryTimeColumnIndex);
                    row.setDraws(Integer.parseInt(entryTime));
                }
                //save in array
                catch (Exception e){
                    row.setDraws(INIT);
                }

                entryTimeColumnIndex = c.getColumnIndex(Tables.winnersTable.PERCENT_OF_WINS);

                //get entry
                try{
                    String entryTime = c.getString(entryTimeColumnIndex);
                    row.setPercent_Win(Double.parseDouble(entryTime));
                }
                //save in array
                catch (Exception e){
                    row.setPercent_Win(INIT);
                }

                arrayListRow.add(row);
            }

        }
        return arrayListRow;
    }

}
