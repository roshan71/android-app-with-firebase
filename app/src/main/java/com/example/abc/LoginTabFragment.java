package com.example.abc;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginTabFragment extends Fragment {
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    // Storing data into SharedPreferences

    private FirebaseAuth mAuth;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
            mAuth=FirebaseAuth.getInstance();
            View view = inflater.inflate(R.layout.fragment_login_tab, container, false);
          Button login_button=(Button) view.findViewById(R.id.login_button);
          EditText login_email=(EditText) view.findViewById(R.id.login_email);
          EditText login_password=(EditText) view.findViewById(R.id.login_password);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPref",MODE_PRIVATE);

          login_button.setOnClickListener(new View.OnClickListener() {

              @Override
              public void onClick(View v) {
                DBManager db =new DBManager(getActivity());
                String Email=login_email.getText().toString();
                String Password=login_password.getText().toString();
                if(!Email.equals("") && !Password.equals("")) {
                    System.out.println(Email);
                    System.out.println(Password);
                    mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                String userId = currentUser.getUid();
                                System.out.println(userId);
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                myEdit.putString("userId",userId);
                                myEdit.commit();
                                Toast.makeText(getActivity(), "Login Successfully", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getActivity(), "Not Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    if (isValidEmail(Email)) {
                        String res = db.validateUser(Email,Password);
                        if(res!=null){
                            login_email.setText("");
                            login_password.setText("");
                            // Creating an Editor object to edit(write to the file)
//                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
//                            myEdit.putString("userId",res);
//                            myEdit.commit();
                            Intent intent = new Intent(getActivity(), MainActivity2.class);
                            startActivity(intent);




                            Toast.makeText(getActivity(), "Login Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getActivity(), "No User Found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Invalid Email", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getActivity(), "All Field is Mandatory", Toast.LENGTH_SHORT).show();
                }
              }

          });

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Toast.makeText(getContext(), "Not User get Successfully", Toast.LENGTH_SHORT).show();

        }
    }




}