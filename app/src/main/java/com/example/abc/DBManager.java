package com.example.abc;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBManager extends SQLiteOpenHelper {
    private static final String dbname="myDatabase.db";
    public DBManager( Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qry="create table User (id integer primary key, email text, password text)";
        db.execSQL(qry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User");
        onCreate(db);
    }

    public boolean createUser(String email,String password){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put("email",email);
        cv.put("password",password);
        long res=db.insert("User",null,cv);
        if(res==-1){
            return false;
        }
        else{
            return true;
        }
    }

    @SuppressLint("Range")
    public boolean validateUser(String email, String password){
        SQLiteDatabase db=this.getReadableDatabase();
        String TABLE_NAME="User";
        String COLUMN_NAME1="email";
        String COLUMN_NAME2="password";

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME1 + " = '" + email + "'";
        Cursor cursor=db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
            if (password.equals(cursor.getString(cursor.getColumnIndex(COLUMN_NAME2)))) {
                cursor.close();
                return true;
            }
        }

            return false;


    }

    public boolean checkEmail(String email){


        SQLiteDatabase db=this.getReadableDatabase();
        String TABLE_NAME="User";
        String COLUMN_NAME="email";

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " = '" + email + "'";
        Cursor c=db.rawQuery(query, null);
        System.out.println(c.getCount());
        if(c.getCount()==0){
            return true;
        }
        else{
            return false;
        }
    }
}
