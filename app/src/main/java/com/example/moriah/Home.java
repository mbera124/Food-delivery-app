package com.example.moriah;

import android.os.Bundle;

import com.example.moriah.Interface.ItemClickListener;
import com.example.moriah.adapters.MenuAdapter;
import com.example.moriah.adapters.MenuViewHolder;
import com.example.moriah.model.Category;
import com.example.moriah.model.PrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    FirebaseDatabase database;
    private FirebaseAuth auth;
    DatabaseReference databaseReference;
    TextView txtfullname;
    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;
    DrawerLayout drawer;

    private MenuAdapter menuAdapter;
    private List<Category> categoryList = new ArrayList<>();
    PrefManager prefManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);
        auth = FirebaseAuth.getInstance();
        prefManager = new PrefManager(this);

        //init firebase
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Category");
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);

        //set name for user
//        View headerView = navigationView.getHeaderView(0);
//        txtfullname = findViewById(R.id.txtfullname);
       // txtfullname.setText(FirebaseApp.getInstance);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
             Toast.makeText(Home.this, "Welcome back "+" "+ prefManager.getUserName()+" ", Toast.LENGTH_LONG).show();
        }

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

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    /**  @Override
    public void onBackpressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    } **/



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



   /** @SuppressWarnings("StatementsWithEmptyBody")
    @Override

    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            return true;
        } else if (id == R.id.nav_cart) {
            return true;
        } else if (id == R.id.nav_orders) {
            return true;
        } else if (id == R.id.nav_logout) {
         return true;

        } else {
            return true;
        }

        //DrawerLayout drawer = findViewById(R.id.drawer_layout);
        //drawer.closeDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);

    }   **/




}
