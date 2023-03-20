package com.example.abc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddTask extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;


    private EditText taskTitleEditText;
    private EditText taskDescriptionEditText;
    private Button addTaskButton;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskTitleEditText = findViewById(R.id.task_title_edit_text);
        taskDescriptionEditText = findViewById(R.id.task_description_edit_text);
        addTaskButton = findViewById(R.id.add_task_button);
        DBManager db=new DBManager(this);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Tasks");
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=taskTitleEditText.getText().toString();
                String desc=taskDescriptionEditText.getText().toString();
                String user=sharedPreferences.getString("userId","");

                Task newTask=new Task(title,desc,user);

                Long tsLong = System.currentTimeMillis() / 1000;
                String ts = tsLong.toString();
                databaseReference.child(ts).setValue(newTask);
                taskTitleEditText.setText("");
                taskDescriptionEditText.setText("");
                Toast.makeText(AddTask.this, "Task Added to firebase", Toast.LENGTH_SHORT).show();
            }
        });

//                String userId=sharedPreferences.getString("userId","");
//
//                if(!title.equals("") && !desc.equals("")){
//
//                    boolean res=db.addTask(title,desc,userId);
//
//                    if(res){
//                        taskTitleEditText.setText("");
//                        taskDescriptionEditText.setText("");
//                        Toast.makeText(AddTask.this, "Task Added", Toast.LENGTH_SHORT).show();
//                    }
//                    else{
//                        Toast.makeText(AddTask.this, "Something Went Worng", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                else
//                {
//                    Toast.makeText(AddTask.this, "All Field is Mandatory", Toast.LENGTH_SHORT).show();
//                }
//

//            }
//        });
        bottomNavigationView =findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.addTask);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.help:
                        startActivity(new Intent(getApplicationContext(),Help.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.tasks:
                        startActivity(new Intent(getApplicationContext(),MainActivity2.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.addTask:
                        return true;
                }
                return false;
            }
        });
    }
}