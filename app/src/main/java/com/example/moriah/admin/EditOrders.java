package com.example.moriah.admin;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moriah.R;
import com.example.moriah.activities.AboutUsActivity;
import com.example.moriah.activities.CartActivity;
import com.example.moriah.activities.OrdersActivity;
import com.example.moriah.activities.UserDashboard;
import com.example.moriah.adapters.EditOrderAdapter;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class EditOrders extends AppCompatActivity implements EditOrderAdapter.onItemClicklistener {
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    private FirebaseAuth auth;
    ProgressDialog mProgressDialog;
    DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    FirebaseRecyclerAdapter<Request, OrdersViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference requests;
    List<Request> foods = new ArrayList<>();
    EditOrderAdapter editOrderAdapter;
    BottomNavigationView bottomNavigationView;
    private String TAG = "Admin";
    private int Selecteditem = 0;
    private static final int NOTIFY_ME_ID=1337;
    NotificationManager mNotificationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_orders);
//        Toast.makeText(this, "orders,", Toast.LENGTH_LONG).show();
//        auth = FirebaseAuth.getInstance();





        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            Log.e(TAG, "Name: " + personName + ", email: " + personEmail + ",Id:" + personId +
                    ", Image: " + personPhoto);
        }
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_orders);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_menu:
                        startActivity(new Intent(getApplicationContext(), UserDashboard.class));
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                    case R.id.navigation_cart:
                        startActivity(new Intent(getApplicationContext(), CartActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                    case R.id.navigation_orders:
                        if (acct.getEmail().equals("josephmbera124@gmail.com")) {
                            startActivity(new Intent(getApplicationContext(), EditOrders.class));
                        } else {
                            startActivity(new Intent(getApplicationContext(), OrdersActivity.class));
                            overridePendingTransition(0, 0);
                        }
                        finish();
                        break;
                    case R.id.navigation_about:
                        startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");
        recyclerView = findViewById(R.id.listorders);
        editOrderAdapter = new EditOrderAdapter(foods, this, this);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //  recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(editOrderAdapter);

        if (foods.size() > 0) {
            foods.clear();
        }

        loadOrders();

    }


    private void loadOrders() {
        showProgressDialog();
        if (foods.size() > 0) {
            foods.clear();
        }

        requests.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                        for (DataSnapshot snapshot : postSnapShot.getChildren()) {
                            if (snapshot != null) {
                                Request request = new Request();
                                request.setContact(snapshot.child("Contact").getValue().toString());
                                request.setStatus(snapshot.child("Status").getValue().toString());
                                request.setTotal(snapshot.child("OrderPrice").getValue().toString());
                                request.setTxtLocationResult(snapshot.child("Location").getValue().toString());
                                request.setUserIdkey(snapshot.child("UserId").getValue().toString());
                                request.setKey(snapshot.child("Mkey").getValue().toString());
                                request.setProductId(snapshot.child("Mkey").getValue().toString());
//                                request.setName(snapshot.child("name").getValue().toString());

                                foods.add(request);
                            }
                        }
                        Toast.makeText(EditOrders.this, "orders,", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "" + dataSnapshot.getChildrenCount());
                    }
                    editOrderAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(EditOrders.this, "orders not exist,", Toast.LENGTH_SHORT).show();
                }
                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(EditOrders.this, "Error", Toast.LENGTH_LONG).show();
                hideProgressDialog();
            }
        });

    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(EditOrders.this);
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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditOrders.this);
        alertDialog.setTitle("Select Action");
        String[] items = {"Received", "On The Way", "Shipped"};
        int checkedItem = 2;

        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case (0):
                        Selecteditem = 0;
                        break;
                    case (1):
                        Selecteditem = 1;
                        break;
                    case (2):
                        Selecteditem = 2;
                        break;


                }
            }

        });

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (Selecteditem == 0) {
                    requests.child(request.getUserIdkey()).child(request.getKey()).child("Status").setValue("Received");
//     editOrderAdapter.notifyDataSetChanged();
                    if (foods.size() > 0) {
                        foods.clear();
                    }
                } else if (Selecteditem == 1) {

                    requests.child(request.getUserIdkey()).child(request.getKey()).child("Status").setValue("On The Way");
                    // request.setStatus("On The Way");
//     editOrderAdapter.notifyDataSetChanged();
                    if (foods.size() > 0) {
                        foods.clear();
                    }

                } else if (Selecteditem == 2) {
                    requests.child(request.getUserIdkey()).child(request.getKey()).child("Status").setValue("Shipped");
//     editOrderAdapter.notifyDataSetChanged();
                    if (foods.size() > 0) {
                        foods.clear();
                    }
                   sendNotify();
                }
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    private void sendNotify() {
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this);
        nBuilder.setContentTitle("MORIAH");
        nBuilder.setContentText("Your order has been processed");
        nBuilder.setTicker("Moriah Notification");
        nBuilder.setAutoCancel(true);
        nBuilder.setSmallIcon(R.drawable.bell);

//        nBuilder.setNumber(++totalMessages);

        Intent intent = new Intent(this, OrdersActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(OrdersActivity.class);

        stackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);
        nBuilder.setContentIntent(pendingIntent);

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFY_ME_ID, nBuilder.build());
    }

}





