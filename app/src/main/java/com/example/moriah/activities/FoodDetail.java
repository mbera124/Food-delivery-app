package com.example.moriah.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.moriah.R;
import com.example.moriah.model.Order;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FoodDetail extends AppCompatActivity {
TextView food_name,food_price,tvdescription;
ImageView food_image;
CollapsingToolbarLayout collapsingToolbarLayout;
FloatingActionButton btncart;
ElegantNumberButton numberButton;

String foodPrice="", foodName=" ", imageUrl="";
FirebaseDatabase database;
DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Menu");
//        databaseReference = database.getReference("Delights");
//        databaseReference = database.getReference("Soft Drinks");

        numberButton=findViewById(R.id.number_button);
        btncart=findViewById(R.id.btnfood);

        food_name=findViewById(R.id.food_name);
        food_price=findViewById(R.id.food_price);
        food_image=findViewById(R.id.food_image);
        tvdescription=findViewById(R.id.tvdescription);

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
