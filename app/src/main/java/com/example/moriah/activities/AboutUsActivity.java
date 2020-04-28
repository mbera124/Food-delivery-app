package com.example.moriah.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moriah.R;
import com.example.moriah.admin.EditOrders;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class AboutUsActivity extends AppCompatActivity {
BottomNavigationView bottomNavigationView;
    private static final String TAG = "AbouUs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            Log.e(TAG, "Name: " + personName + ", email: " + personEmail
                    + ", Image: " + personPhoto);
        }
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_menu:
                        startActivity(new Intent(getApplicationContext(), UserDashboard.class));
                        overridePendingTransition(0,0);
                        finish();
                        break;
                    case R.id.navigation_cart:
                        startActivity(new Intent(getApplicationContext(), CartActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        break;
                    case R.id.navigation_orders:
                        if (acct.getEmail().equals("josephmbera124@gmail.com")) {
                            startActivity(new Intent(getApplicationContext(), EditOrders.class));
                        }else{
                            startActivity(new Intent(getApplicationContext(), OrdersActivity.class));
                            overridePendingTransition(0,0);}
                        finish();
                        break;
                    case R.id.navigation_about:
                        startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });


    }
}
