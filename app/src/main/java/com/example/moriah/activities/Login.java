package com.example.moriah.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moriah.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 1001;

    GoogleSignInClient googleSignInClient;
    EditText edtemail, edtpassword;
    Button btnsignin, btnsignup;
    SignInButton btngooglesignin;
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
        btngooglesignin = findViewById(R.id.btngooglesignin);
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(Login.this, UserDashboard.class));
            finish();
        }


//        btnsignup.setOnClickListener(v -> {
//            startActivity(new Intent(Login.this, Signup.class));
//            finish();
//        });

//        btnsignin.setOnClickListener(v -> {
//
//            String email = edtemail.getText().toString();
//            final String password = edtpassword.getText().toString();
//            if (TextUtils.isEmpty(email)) {
//                edtemail.setError("Enter E-Mail");
//                edtemail.requestFocus();
//                return;
//            }
//            if (TextUtils.isEmpty(password)) {
//                edtpassword.setError("Enter Password");
//                edtpassword.requestFocus();
//                return;
//            }
//
//            final ProgressDialog mDialog = new ProgressDialog(Login.this);
//            mDialog.setMessage("Signing In...");
//            mDialog.show();
//
//            //authenticate user
//            auth.signInWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(Login.this, task -> {
//
//                        if (!task.isSuccessful()) {
//                            // there was an error
//                            if (password.length() < 6) {
//                                edtpassword.setError("Password too short");
//                            } else {
//                                mDialog.dismiss();
//                                Toast.makeText(Login.this, "Authentication failed, check your email and password or sign up", Toast.LENGTH_LONG).show();
//                            }
//
//                        } else {
//                            if(email.equalsIgnoreCase("administrator@gmail.com")){
//                                startActivity(new Intent(Login.this, OrdersActivity.class));
//                                finish();}
//                            mDialog.dismiss();
//                            Intent intent = new Intent(Login.this, UserDashboard.class);
//                            startActivity(intent);
//                            finish();
//                        }
//                    });
//        });

        btngooglesignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch Sign In
                signInToGoogle();
            }
        });

        // Configure Google Client
        configureGoogleClient();
    }

    private void configureGoogleClient() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                // for the requestIdToken, this is in the values.xml file that
                // is generated from your google-services.json
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        // Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.btngooglesignin);
        signInButton.setSize(SignInButton.SIZE_WIDE);

        // Initialize Firebase Auth
//        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            Log.d(TAG, "Currently Signed in: " + currentUser.getEmail());
            showToastMessage("Currently Logged in: " + currentUser.getEmail());
        }
    }

    public void signInToGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                showToastMessage("Google Sign in Succeeded");
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                showToastMessage("Google Sign in Failed " + e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = auth.getCurrentUser();

                            Log.d(TAG, "signInWithCredential:success: currentUser: " + user.getEmail());

                            showToastMessage("Firebase Authentication Succeeded ");
                            startActivity(new Intent(Login.this, UserDashboard.class));
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.

                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                            showToastMessage("Firebase Authentication failed:" + task.getException());
                        }
                    }
                });
    }

    private void showToastMessage(String message) {
        Toast.makeText(Login.this, message, Toast.LENGTH_LONG).show();
    }

        @Override
        public void onBackPressed() {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Login.this);
            alertDialog.setTitle("MORIAH");
            alertDialog.setMessage("Do you want to exit?");
            alertDialog.setIcon(R.drawable.cart);
            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    System.exit(0);
                        }



            });
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
        }

        }

