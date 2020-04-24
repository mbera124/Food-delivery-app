package com.example.moriah.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moriah.R;
import com.google.firebase.auth.FirebaseAuth;

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
            startActivity(new Intent(Login.this, UserDashboard.class));
            finish();
        }


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
                    .addOnCompleteListener(Login.this, task -> {

                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 6) {
                                edtpassword.setError("Password too short");
                            } else {
                                mDialog.dismiss();
                                Toast.makeText(Login.this, "Authentication failed, check your email and password or sign up", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            if(email.equalsIgnoreCase("administrator@gmail.com")){
                                startActivity(new Intent(Login.this, OrdersActivity.class));
                                finish();}
                            mDialog.dismiss();
                            Intent intent = new Intent(Login.this, UserDashboard.class);
                            startActivity(intent);
                            finish();
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
