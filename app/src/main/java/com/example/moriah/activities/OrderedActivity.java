package com.example.moriah.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moriah.R;
import com.example.moriah.adapters.OrderedAdapter;
import com.example.moriah.admin.EditOrders;
import com.example.moriah.model.Request;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderedActivity extends AppCompatActivity {
    CardView cardViewmeals;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    RecyclerView recycler_meal;
    private OrderedAdapter orderedAdapter;
    private List<Request> requestList = new ArrayList<>();
    BottomNavigationView bottomNavigationView;
    GoogleSignInClient googleSignInClient;
    private static final String TAG = "OrderedActivity";
    String userId = " ", orderId = "";
    GoogleSignInAccount acct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordered);
        cardViewmeals=findViewById(R.id.cardviewmeals);
        googleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN);

         acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();

            Log.e(TAG, "Name: " + personName + ", email: " + personEmail + ",Id: " +personId);

        }

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            orderId = bundle.getString("orderId");

        }
        //orderId =  "1591390643633";
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
        database = FirebaseDatabase.getInstance();
        //database.setPersistenceEnabled(true);
        String skey = acct.getId();
        //String skey = "102354684639490738615";
         userId = skey;
         if(skey != null) {
//             Log.d("testtt", orderId);
  //           Log.d("testtt", skey);
             databaseReference = database.getReference("Requests").child(skey).child(orderId);
         }
        //breakfast
        orderedAdapter = new OrderedAdapter(this, requestList);
        recycler_meal = findViewById(R.id.recycler_meal);
        recycler_meal.setHasFixedSize(true);
        recycler_meal.setNestedScrollingEnabled(false);
//        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
       RecyclerView.LayoutManager layoutManagerb = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
        recycler_meal.setLayoutManager(layoutManagerb);
        recycler_meal.setAdapter(orderedAdapter);
        loadOrdered();
    }

    private void loadOrdered() {
        if (requestList.size() > 0) {
            requestList.clear();
        }
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              //  requestList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Log.d("snap", ""+ snapshot.getChildrenCount());
                            if(snapshot.getChildrenCount() == 11){
                                // Request request = snapshot.getValue(Request.class);
                                Request request =new Request();
                                request.setProductName(snapshot.child("productName").getValue().toString());
                                request.setQuantity(snapshot.child("quantity").getValue().toString());
                                request.setTotalOrderPrice(snapshot.child("totalOrderPrice").getValue().toString());
//                                request.setTotal(snapshot.child("total").getValue().toString());
                                 requestList.add(request);
                            }


                       // Request request = mdataSnapshot.getValue(Request.class);
                       // requestList.add(request);
                    }
                }
//                hideProgressDialog();
                Toast.makeText(OrderedActivity.this, "" + requestList.size(), Toast.LENGTH_LONG).show();
                Log.d("orderssize" , "" +requestList.size() );
                orderedAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
