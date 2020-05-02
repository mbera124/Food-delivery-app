package com.example.moriah.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import com.example.moriah.R;
import com.example.moriah.adapters.OrdersAdapter;
import com.example.moriah.admin.EditOrders;
import com.example.moriah.model.Request;
import com.example.moriah.viewholders.OrdersViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity implements OrdersAdapter.onItemClicklistener{
    public RecyclerView recyclerView;
    public LayoutManager layoutManager;
    private FirebaseAuth auth;
    ProgressDialog mProgressDialog;
    DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    FirebaseRecyclerAdapter<Request, OrdersViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference requests;
    List<Request> foods = new ArrayList<>();
    OrdersAdapter ordersAdapter;
    BottomNavigationView bottomNavigationView;
    private static final String TAG = OrdersActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        if (foods.size() > 0) {
            foods.clear();
        }
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            Log.e(TAG, "Name: " + personName + ", email: " + personEmail+ ",Id:"+ personId+ ", Image: " + personPhoto);
        }
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_orders);
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
//        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests").child(acct.getId());
        recyclerView = findViewById(R.id.listorders);
        ordersAdapter = new OrdersAdapter(foods, this,this);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(ordersAdapter);
        loadOrders();

    }


    private void loadOrders() {
        showProgressDialog();
        if (foods.size() > 0) {
            foods.clear();
        }
        requests.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (foods.size()>0)
                    {
                        foods.clear();
                    }
                    for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                        if (postSnapShot != null) {
                            Request request = new Request();
                           // Log.d("contact", ""+postSnapShot);
                            Log.d("contact", ""+postSnapShot.child("Contact").getValue().toString());
                            request.setContact(postSnapShot.child("Contact").getValue().toString());
                            request.setProductId(postSnapShot.child("Mkey").getValue().toString());
                            request.setStatus(postSnapShot.child("Status").getValue().toString());
                            request.setTotal(postSnapShot.child("OrderPrice").getValue().toString());
                            request.setTxtLocationResult(postSnapShot.child("Location").getValue().toString());
                            foods.add(request);

                        }

                    }
                    ordersAdapter.notifyDataSetChanged();
                }
                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(OrdersActivity.this, "Error", Toast.LENGTH_LONG).show();
                hideProgressDialog();
            }
        });

    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(OrdersActivity.this);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }


    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onItemClick(Request request) {
//        startActivity(new Intent(this,TrackOrder.class ));
    }
}






