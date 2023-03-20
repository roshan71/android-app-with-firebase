package com.example.abc;

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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SIgnupTabFragment extends Fragment {
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_s_ignup_tab, container, false);
        Button signup_button=(Button) view.findViewById(R.id.signup_button);
        EditText signup_email=(EditText) view.findViewById(R.id.signup_email);
        EditText signup_confirm=(EditText) view.findViewById(R.id.signup_confirm);
        EditText signup_password=(EditText) view.findViewById(R.id.signup_password);
      signup_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DBManager db =new DBManager(getActivity());
                String Email=signup_email.getText().toString();
                String Password=signup_password.getText().toString();
                String conPassword=signup_confirm.getText().toString();

                if(Email.equals("") && Password.equals("")&& conPassword.equals("")){
                    Toast.makeText(getActivity(), "All Field is Mandatory", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(isValidEmail(Email) ) {
                        if(Password.length()>6) {


                            if (Password.equals(conPassword)) {
                                mAuth=FirebaseAuth.getInstance();
                                mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(getActivity(), "User Created in FIreAuth Successfully", Toast.LENGTH_SHORT).show();

                                        }
                                        else{
                                            Toast.makeText(getActivity(), "Something Went Wrong5dtx", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });


                                Boolean re = db.checkEmail(Email);
                                System.out.println(re);

                                if (re) {
                                    boolean res = db.createUser(Email, Password);

                                    System.out.println(res);
                                    if (res) {
                                        signup_email.setText("");
                                        signup_password.setText("");
                                        signup_confirm.setText("");
                                        Long tsLong = System.currentTimeMillis() / 1000;
                                        String ts = tsLong.toString();
                                        mAuth=FirebaseAuth.getInstance();
                                        mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(getActivity(), "User Created in FIreAuth Successfully", Toast.LENGTH_SHORT).show();

                                                }
                                                else{
                                                    Toast.makeText(getActivity(), "Something Went Wrong5dtx", Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                        });
                                        Toast.makeText(getActivity(), "User Created Successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getActivity(), "Unable to Create User", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getActivity(), "User Already Exists", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(getActivity(), "Confirm Password not match", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(getActivity(), "Password Length Should be Greater than 6", Toast.LENGTH_SHORT).show();

                        }
                    }
                    else{
                        Toast.makeText(getActivity(), "Invalid Email", Toast.LENGTH_SHORT).show();
                    }
                }


            }

        });

        return view;
    }
}