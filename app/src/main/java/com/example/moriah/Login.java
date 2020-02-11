package com.example.moriah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moriah.model.Common;
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

public class Login extends AppCompatActivity {
EditText edtemail,edtpassword;
Button btnsignin,btnsignup;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();

        edtemail = findViewById(R.id.edtemail);
        edtpassword = findViewById(R.id.edtpassword);
        btnsignin = findViewById(R.id.btnsignin);
        btnsignup = findViewById(R.id.btnsignup);
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(Login.this, Home.class));
            finish();
        }
        //init firebase
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        final DatabaseReference table_user = database.getReference("User");

        btnsignup.setOnClickListener(v -> {
            startActivity(new Intent(Login.this, Signup.class));
            finish();
        });

        btnsignin.setOnClickListener(v -> {

            String email = edtemail.getText().toString();
            final String password = edtpassword.getText().toString();
            if (TextUtils.isEmpty(email)) {
                edtemail.setError("Enter E-Mail");
                edtemail.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                edtpassword.setError("Enter Password");
                edtpassword.requestFocus();
                return;
            }

            final ProgressDialog mDialog = new ProgressDialog(Login.this);
            mDialog.setMessage("Signing In...");
            mDialog.show();

            //authenticate user
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()) {
                                // there was an error
                                if (password.length() < 6) {
                                    edtpassword.setError("Password too short");
                                } else {
                                    mDialog.dismiss();
                                    Toast.makeText(Login.this, "Authentication failed, check your email and password or sign up", Toast.LENGTH_LONG).show();
                                }

                            } else {
                                mDialog.dismiss();
                                Intent intent = new Intent(Login.this, Home.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
        });
    }

        @Override
        public void onBackPressed() {
            AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
            builder.setTitle(R.string.app_name);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setMessage("Do you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, id) -> finish())
                    .setNegativeButton("No", (dialog, id) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();

        }

    }
