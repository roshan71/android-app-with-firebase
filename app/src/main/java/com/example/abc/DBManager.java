package com.example.abc;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.*;

public class DBManager extends SQLiteOpenHelper {
    private static final String dbname="myDatabase.db";
    public DBManager( Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qry="create table User (id String primary key, email text, password text)";
        db.execSQL(qry);
        String qry1="create table Tasks (task_id String primary key, title text, description text,user_id String)";
        db.execSQL(qry1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS Tasks");
        onCreate(db);

    }

    public boolean createUser(String email,String password){
        SQLiteDatabase db=this.getWritableDatabase();
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        ContentValues cv=new ContentValues();
        cv.put("id",ts);
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
    public String validateUser(String email, String password){
        SQLiteDatabase db=this.getReadableDatabase();
        String TABLE_NAME="User";
        String COLUMN_NAME1="email";
        String COLUMN_NAME2="password";

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME1 + " = '" + email + "'";
        Cursor cursor=db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
            if (password.equals(cursor.getString(cursor.getColumnIndex(COLUMN_NAME2)))) {

                String id= cursor.getString(0);
                cursor.close();
                return id;
            }
        }

            return null;


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

    public boolean addTask(String title,String desc, String user_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        ContentValues cv=new ContentValues();
        cv.put("task_id",ts);
        cv.put("title",title);
        cv.put("description",desc);
        cv.put("user_id",user_id);
        long res=db.insert("Tasks",null,cv);
        if(res==-1){
            return false;
        }
        else {
            return true;
        }
    }
    public List<HashMap<String, String>> getAllTodoItems(String user) {
        List<HashMap<String, String>> todoList = new ArrayList<>();
        String TABLE_NAME="Tasks";
        String COLUMN_NAME="user_id";
        String userId=user;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " = '" + userId + "'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String description = cursor.getString(2);

                HashMap<String, String> todoMap = new HashMap<>();
                todoMap.put("id", String.valueOf(id));
                todoMap.put("title", title);
                todoMap.put("description", description);
                todoList.add(todoMap);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return todoList;
    }

    public boolean deleteTask(String task_id){
        SQLiteDatabase db = this.getWritableDatabase();
        String TABLE_NAME="Tasks";
        String COLUMN_NAME="task_id";
        String query = "Delete  FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " = '" + task_id + "'";

        Cursor res=db.rawQuery(query, null);
        System.out.println("usduvgg_____sudhvud___usv i__");

        System.out.println(res.getCount());

        return true;
    }




}
