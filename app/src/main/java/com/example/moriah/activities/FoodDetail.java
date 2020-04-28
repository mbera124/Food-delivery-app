package com.example.moriah.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.moriah.R;
import com.example.moriah.admin.EditOrders;
import com.example.moriah.model.Order;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FoodDetail extends AppCompatActivity {
TextView food_name,food_price,tvquantity;
ImageView food_image;
CollapsingToolbarLayout collapsingToolbarLayout;
Button btncart;
ElegantNumberButton numberButton;

String foodPrice="", foodName=" ", imageUrl="";
FirebaseDatabase database;
DatabaseReference databaseReference;
BottomNavigationView bottomNavigationView;
    private static final String TAG = FoodDetail.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Menu");
        numberButton=findViewById(R.id.number_button);
        btncart=findViewById(R.id.btnaddtocart);
        food_name=findViewById(R.id.food_name);
        food_price=findViewById(R.id.food_price);
        food_image=findViewById(R.id.food_image);
        tvquantity=findViewById(R.id.tvquantity);
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



        btncart.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String foodieName = " ", foodiePrice = " ", foodieQuantity = "", foodTotal = "";
        if (!food_name.getText().toString().equals("")) {
            foodieName = food_name.getText().toString();
        }
        if (!food_price.getText().toString().equals("")) {
            foodiePrice = food_price.getText().toString();
        }
        foodieQuantity = numberButton.getNumber();
        Double total = Double.parseDouble(foodiePrice) * Double.parseDouble(foodieQuantity);
        foodTotal = Double.toString(total);

        Order order = new Order(foodieName, foodiePrice, foodieQuantity, foodTotal);

        Order.addOrder(order);
        Toast.makeText(FoodDetail.this, "Added to Cart", Toast.LENGTH_SHORT).show();
             }
        });



        
        if (getIntent().getExtras()!= null)
//        foodId=getIntent().getStringExtra("FoodId");
        {
           foodName = getIntent().getStringExtra("item_name");
            imageUrl = getIntent().getStringExtra("image_url");
            foodPrice = getIntent().getStringExtra("item_price");

        }

        getFoodDetail();

    }


    private void getFoodDetail() {

//        collapsingToolbarLayout.setTitle(foodName);
        food_price.setText(foodPrice);
        food_name.setText(foodName);
        Picasso.get().load(imageUrl).placeholder(R.drawable.strawberry).into(food_image);



    }


}
