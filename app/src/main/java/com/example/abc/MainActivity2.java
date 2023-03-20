package com.example.abc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    RecyclerView todoRecyclerView;
    List<HashMap<String, String>> todoList = new ArrayList<>();
    TodoAdapter todoAdapter;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    BottomNavigationView bottomNavigationView;

    public void reload(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading tasks...");
        progressDialog.show();

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        String user=sharedPreferences.getString("userId","");

        databaseReference.orderByChild("userId").equalTo(user).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                todoList.clear();
                for (DataSnapshot taskSnapshot : snapshot.getChildren()) {
                    String taskId = taskSnapshot.getKey();
                    String title = taskSnapshot.child("title").getValue(String.class);
                    String desc = taskSnapshot.child("desc").getValue(String.class);
                    String userId = taskSnapshot.child("userId").getValue(String.class);
                    HashMap<String, String> taskMap = new HashMap<>();
                    taskMap.put("task_id", taskId);
                    taskMap.put("title", title);
                    taskMap.put("description", desc);
                    taskMap.put("user_id", userId);
                    todoList.add(taskMap);
                }
                todoAdapter.updateData(todoList);
                progressDialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Tasks");

        todoRecyclerView = findViewById(R.id.recyclerview);
        todoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        todoRecyclerView.setHasFixedSize(true);
        reload();
        todoAdapter = new TodoAdapter(MainActivity2.this, todoList, MainActivity2.this);
        todoRecyclerView.setAdapter(todoAdapter);

        SwipeToDeleteAndEditCallback swipeCallback = new SwipeToDeleteAndEditCallback(todoAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCallback);
        itemTouchHelper.attachToRecyclerView(todoRecyclerView);

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.tasks);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.help:
                        startActivity(new Intent(getApplicationContext(),Help.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.tasks:
//                        reload();
                        return true;

                    case R.id.addTask:
                        startActivity(new Intent(getApplicationContext(),AddTask.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}
