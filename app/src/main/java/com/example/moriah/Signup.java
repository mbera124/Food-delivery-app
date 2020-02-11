package com.example.moriah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moriah.model.PrefManager;
import com.example.moriah.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Signup extends AppCompatActivity {
    EditText edtphone, edtname, edtpassword, edtemail;
    Button btnsignin, btnsignup;
    private FirebaseAuth auth;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        edtname = findViewById(R.id.edtname);
        edtphone = findViewById(R.id.edtphone);
        edtemail = findViewById(R.id.edtemail);
        edtpassword = findViewById(R.id.edtpassword);
        btnsignin = findViewById(R.id.btnsignin);
        btnsignup = findViewById(R.id.btnsignup);
        prefManager = new PrefManager(this);

        auth = FirebaseAuth.getInstance();

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        final DatabaseReference table_user = database.getReference("User");

        btnsignin.setOnClickListener(v -> {
            startActivity(new Intent(Signup.this, Login.class));
            finish();
        });

        btnsignup.setOnClickListener(v -> {

            String name = edtname.getText().toString();
            String email = edtemail.getText().toString();
            String phone = edtphone.getText().toString();
            String password = edtpassword.getText().toString();

           /** if (TextUtils.isEmpty(name)) {
                edtphone.setError("Enter Full Name");
                edtphone.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(email)) {
                edtemail.setError("Enter E-Mail");
                edtemail.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(phone)) {
                edtphone.setError("Enter Phone Number");
                edtphone.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                edtpassword.setError("Enter Password");
                edtpassword.requestFocus();
                return;
            } **/

            final ProgressDialog mDialog = new ProgressDialog(Signup.this);
            mDialog.setMessage("Creating Account...");
            mDialog.show();

            //create user
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {

                        if (task.isSuccessful()) {
                            mDialog.dismiss();
                            Toast.makeText(Signup.this, "User created successfully!!" , Toast.LENGTH_SHORT).show();
                            prefManager.setUserName(name);
                            startActivity(new Intent(Signup.this, Home.class));
                            finish();
                        }
                          else {
                            Toast.makeText(Signup.this, "Authentication failed." ,
                                    Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        }
                    });
        });
    }
}

