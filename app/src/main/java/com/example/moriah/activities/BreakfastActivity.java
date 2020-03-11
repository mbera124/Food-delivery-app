package com.example.moriah.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moriah.R;
import com.example.moriah.adapters.BreakfastAdapter;
import com.example.moriah.adapters.MenuAdapter;
import com.example.moriah.model.Breakfast;
import com.example.moriah.model.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BreakfastActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    RecyclerView recycler_breakfast;
    RecyclerView.LayoutManager layoutManager;

    private BreakfastAdapter breakfastAdapter;
    private List<Breakfast> breakfastList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Breakfast Menu");
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Breakfast");

//        FloatingActionButton fab = findViewById(R.id.fabbreakfast);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent= new Intent(BreakfastActivity.this,CartActivity.class);
//                startActivity(intent);
//            }
//        });

     breakfastAdapter= new BreakfastAdapter(this, breakfastList);

        //load menu
        recycler_breakfast = findViewById(R.id.recycler_breakfast);
        recycler_breakfast.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 2);
        recycler_breakfast.setLayoutManager(layoutManager);
        recycler_breakfast.setAdapter(breakfastAdapter);
        loadBreakfast();

    }

    private void loadBreakfast(){
        if(breakfastList.size() >0){
            breakfastList.clear();
        }
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot mdataSnapshot : dataSnapshot.getChildren()){
                        Breakfast breakfast = mdataSnapshot.getValue(Breakfast.class);
                        breakfastList.add(breakfast);
                    }


                }
                //Toast.makeText(Home.this, "" + categoryList.size(), Toast.LENGTH_SHORT).show();
                breakfastAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
