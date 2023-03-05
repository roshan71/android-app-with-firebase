package com.example.abc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    RecyclerView todoRecyclerView;
    List<HashMap<String, String>> todoList = new ArrayList<>();
    TodoAdapter todoAdapter;
    BottomNavigationView bottomNavigationView;
    public void reload(){
        todoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        todoRecyclerView.setHasFixedSize(true);


        todoList = getTodoListFromSQLite();

        // set the adapter to the RecyclerView
        todoAdapter = new TodoAdapter(this,todoList,this);
        todoRecyclerView.setAdapter(todoAdapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        // initialize the RecyclerView
        todoRecyclerView = findViewById(R.id.recyclerview);
        reload();
        bottomNavigationView =findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.tasks);
        SwipeToDeleteAndEditCallback swipeCallback = new SwipeToDeleteAndEditCallback(todoAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCallback);
        itemTouchHelper.attachToRecyclerView(todoRecyclerView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.help: {
                        startActivity(new Intent(getApplicationContext(),Help. class));
                        overridePendingTransition(0, 0);
                        return true;
                    }
                    case R.id.tasks:

                        return true;

                    case R.id.addTask:{
                        startActivity(new Intent(getApplicationContext(),AddTask.class));
                        overridePendingTransition(0,0);
                        return true;}
                }
                return false;
            }
        });

    }

    private List<HashMap<String, String>> getTodoListFromSQLite() {
        DBManager db =new DBManager(this);
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        List<HashMap<String, String>> todoList = db.getAllTodoItems(sharedPreferences.getString("userId",""));
        return todoList;
    }




    private void showAddTaskForm(HashMap<String, String> todoMap) {

    }
}