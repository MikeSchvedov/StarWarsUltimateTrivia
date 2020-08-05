package com.neoxcoding.mytrivia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class MySQLiteManager extends SQLiteOpenHelper {


    Context context;
    final static String DB_NAME = "starWarsTrivia.db";
    final static String TABLE_NAME_HIGHSCORE = "highscoreTable";
    public final static String COLUMN_ITEM_NAME_HIGHSCORE = "highScore";
    private static SQLiteDatabase myDB;

    public MySQLiteManager(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
        myDB = getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlStament = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_HIGHSCORE + " ";
        sqlStament += "(id INTEGER PRIMARY KEY AUTOINCREMENT, highScore INTEGER DEFAULT 0 )";
        db.execSQL(sqlStament);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //do not touch, be ware it can delete the table from the data base.
    }


    public boolean deleteOldData(){

        myDB.execSQL("delete from "+ TABLE_NAME_HIGHSCORE);

        return true;

    }



    public boolean setHighScore(int newHighScore) {

        //TODO check if new high score is bigger then old inside method or at game over screen

        //  myDB.execSQL("delete from highscoreTable");
        ContentValues contentValues = new ContentValues();
        //insert data by key and value
        contentValues.put(COLUMN_ITEM_NAME_HIGHSCORE, newHighScore);
        //put our values into table a getting a result of row id
        //if the result if -1, we have an error
        if (myDB.insert(TABLE_NAME_HIGHSCORE, null, contentValues) != -1) {
            Log.e("addItem: ", "success!!!");
            return true;
        } else {
            Log.e("addItem: ", "failed!!!");
        }
        return false;
    }



    public int getHighScore() {
       int highScore = 0;
        Cursor cursor = myDB.rawQuery("SELECT * FROM " + TABLE_NAME_HIGHSCORE, null);
        if(cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {
                int hs = cursor.getInt(cursor.getColumnIndex(COLUMN_ITEM_NAME_HIGHSCORE));
                highScore = hs;
                cursor.moveToNext();
            }
        }
        cursor.close();
        return highScore;
    }
}





/*


    public boolean createUser(String userName, String userPass){
        ContentValues contentValues = new ContentValues();
        //insert data by key and value
        contentValues.put("userName",userName);
        contentValues.put("userPass", userPass);
        //put our values into table a getting a result of row id
        //if the result if -1, we have an error
        long res= myDB.insert(TABLE_NAME,null,contentValues);
        //check if we have en error
        return res!=(-1);
    }

    public List<String> getUsers(){
        //create an empty list
        List<String> myList = new ArrayList<>();
        //using Cursor to hold our raw (Cursor is colleciton type object)
        Cursor res= myDB.rawQuery("SELECT * FROM users",null);
        if (res.moveToFirst()){
            do{
                String item = res.getString(res.getColumnIndex("userName"));
                myList.add(item);
            } while (res.moveToNext());
        }
        return myList;
    }

    public void deleteUser(String userName,Boolean isMask){
        if (isMask){
            myDB.execSQL("DELETE FROM users WHERE userName LIKE '%" + userName + "%'");
        } else {
            myDB.execSQL("DELETE FROM users WHERE userName='" + userName + "'");
        }
    }

    public boolean exists(String itemName) {
        //check if an item with the same name exists in the database
        String query = String.format("SELECT * FROM %s WHERE %s = '%s'",
                TABLE_NAME, COLUMN_ITEM_NAME, itemName);

        Cursor cursor = myDB.rawQuery(query, null);
        boolean b = cursor.moveToFirst();
        cursor.close();
        return b;
    }

*/

