package com.example.moriah.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.moriah.R;
import com.example.moriah.adapters.MenuAdapter;
import com.example.moriah.model.Breakfast;
import com.example.moriah.model.Category;
import com.example.moriah.model.PrefManager;

import android.provider.ContactsContract;
import android.provider.Settings;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    FirebaseDatabase database;

    DatabaseReference databaseReference;
  //  TextView txtfullname;
    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;
    DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;


    private MenuAdapter menuAdapter;
    private List<Category> categoryList = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);


        //init firebase
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Category");
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(view -> {
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
//        });

        dl =  findViewById(R.id.drawer_layout);
        t = new ActionBarDrawerToggle(this, dl,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dl.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nv =findViewById(R.id.nv);

        nv.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch(id)
            {
                case R.id.nav_dashboard:
                    startActivity(new Intent(Home.this, UserDashboard.class));
                    finish();
                case R.id.nav_menu:
                    startActivity(new Intent(Home.this, Home.class));
                    finish();
                case R.id.nav_cart:
                    startActivity(new Intent(Home.this, CartActivity.class));
                    finish();
                case R.id.nav_orders:
                    startActivity(new Intent(Home.this, OrdersActivity.class));
                    finish();
                    finish();
                case R.id.nav_logout:
                    finish();
                    System.exit(0);
//                    auth.signOut();
//                    Intent i = new Intent(getApplicationContext(),Login.class);
//                    startActivity(i);
                default:
                    return true;
            }
        });




        //set name for user
//        View headerView = navigationView.getHeaderView(0);
//        txtfullname = findViewById(R.id.txtfullname);
       // txtfullname.setText(FirebaseApp.getInstance);


        menuAdapter = new MenuAdapter(this, categoryList);

        //load menu
        recycler_menu = findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 2);
        recycler_menu.setLayoutManager(layoutManager);
        recycler_menu.setAdapter(menuAdapter);
        loadMenu();


    }

    private void loadMenu() {


      if(categoryList.size() >0){
          categoryList.clear();
      }
      databaseReference.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              if(dataSnapshot.exists()){
                   for (DataSnapshot mdataSnapshot : dataSnapshot.getChildren()){
                       Category category = mdataSnapshot.getValue(Category.class);
                       categoryList.add(category);
                   }


              }
              //Toast.makeText(Home.this, "" + categoryList.size(), Toast.LENGTH_SHORT).show();
              menuAdapter.notifyDataSetChanged();
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });

    }

    @Override
    public void onBackPressed() {

        if (dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
            super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}

