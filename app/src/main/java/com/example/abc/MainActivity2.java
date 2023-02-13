package com.example.abc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

public class MainActivity2 extends AppCompatActivity implements TodoAdapter.OnTodoClickListener{
    RecyclerView todoRecyclerView;
    List<HashMap<String, String>> todoList = new ArrayList<>();
    TodoAdapter todoAdapter;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        // initialize the RecyclerView
        todoRecyclerView = findViewById(R.id.recyclerview);
        todoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        todoRecyclerView.setHasFixedSize(true);


        todoList = getTodoListFromSQLite();

        // set the adapter to the RecyclerView
        todoAdapter = new TodoAdapter(todoList, this);
        todoRecyclerView.setAdapter(todoAdapter);
        bottomNavigationView =findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.tasks);


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
    @Override
    public void onDeleteClick(HashMap<String, String> todoMap) {
        int index = todoList.indexOf(todoMap);

        // Remove the Todo task from the list
        todoList.remove(index);

        // Notify the adapter that the data has changed
        todoAdapter.notifyItemRemoved(index);
        System.out.println("todoMap123");
        DBManager db =new DBManager(this);
        // Also, delete the Todo task from the SQLite database
        db.deleteTask(todoMap.get("id"));
        System.out.println("idvisdivbsdivbiosdviodgbhdiofviodfhngr");
        // delete the Todo task from the SQLite database
//       deleteTodoFromSQLite(todoMap);
////
//        // update the list in the adapter
        todoList.remove(todoMap);
        todoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onEditClick(HashMap<String, String> todoMap) {
        System.out.println("audsgicufgdsiuvguidsv");
        // show the add task form with the data to edit
//        showAddTaskForm(todoMap);
    }


    private List<HashMap<String, String>> getTodoListFromSQLite() {
        DBManager db =new DBManager(this);
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        List<HashMap<String, String>> todoList = db.getAllTodoItems(sharedPreferences.getString("userId",""));
        return todoList;
    }


    private void deleteTodoFromSQLite(HashMap<String, String> todoMap) {
        // First, get the index of the Todo task to be deleted
        int index = todoList.indexOf(todoMap);

        // Remove the Todo task from the list
        todoList.remove(index);

        // Notify the adapter that the data has changed
        todoAdapter.notifyItemRemoved(index);
        DBManager db =new DBManager(this);
        // Also, delete the Todo task from the SQLite database
        db.deleteTask(todoMap.get("id"));
        System.out.println("idvisdivbsdivbiosdviodgbhdiofviodfhngr");
    }

    // method to show the add task form with the data to edit
    private void showAddTaskForm(HashMap<String, String> todoMap) {
        // implement the code to show the add task form
    }
}