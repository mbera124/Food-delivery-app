package com.example.moriah.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moriah.R;
import com.example.moriah.adapters.LunchAdapter;
import com.example.moriah.model.Lunch;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LunchActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    RecyclerView recycler_lunch;
    RecyclerView.LayoutManager layoutManager;

    private LunchAdapter lunchAdapter;
    private List<Lunch> lunchList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Lunch Menu");
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Lunch");

        FloatingActionButton fab = findViewById(R.id.fablunch);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());


        lunchAdapter= new LunchAdapter(this, lunchList);

        //load menu
        recycler_lunch = findViewById(R.id.recycler_lunch);
        recycler_lunch.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 2);
        recycler_lunch.setLayoutManager(layoutManager);
        recycler_lunch.setAdapter(lunchAdapter);
        loadLunch();

    }

    private void loadLunch(){
        if(lunchList.size() >0){
          lunchList.clear();
        }
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot mdataSnapshot : dataSnapshot.getChildren()){
                     Lunch lunch = mdataSnapshot.getValue(Lunch.class);
                       lunchList.add(lunch);
                    }


                }
                //Toast.makeText(Home.this, "" + categoryList.size(), Toast.LENGTH_SHORT).show();
                lunchAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}