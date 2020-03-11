package com.example.moriah.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.moriah.R;
import com.example.moriah.adapters.DrinksAdapter;
import com.example.moriah.model.SoftDrinks;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SoftDrinksActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    RecyclerView recycler_drinks;
    RecyclerView.LayoutManager layoutManager;

    private DrinksAdapter drinksAdapter;
    private List<SoftDrinks> drinksList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_drinks);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Drinks Menu");
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fabdrink);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(SoftDrinksActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });


        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Softdrinks");




        drinksAdapter= new DrinksAdapter(this, drinksList);

        //load menu
        recycler_drinks = findViewById(R.id.recycler_drink);
        recycler_drinks .setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 2);
        recycler_drinks .setLayoutManager(layoutManager);
        recycler_drinks .setAdapter(drinksAdapter);
        loadDrinks();

    }

    private void loadDrinks(){
        if(drinksList.size() >0){
           drinksList.clear();
        }
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot mdataSnapshot : dataSnapshot.getChildren()){
                       SoftDrinks softDrinks= mdataSnapshot.getValue(SoftDrinks.class);
                       drinksList.add(softDrinks);
                    }


                }
                Toast.makeText(SoftDrinksActivity.this, "" + drinksList.size(), Toast.LENGTH_LONG).show();
                drinksAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}