package com.example.moriah.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.moriah.R;
import com.example.moriah.adapters.MenuAdapter;
import com.example.moriah.admin.AddCategory;
import com.example.moriah.admin.EditOrders;
import com.example.moriah.model.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    FirebaseDatabase database;
    ProgressDialog mProgressDialog;
private FloatingActionButton fabmenu;
    DatabaseReference databaseReference;
  //  TextView txtfullname;
    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;
    DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private FirebaseAuth auth;


    private MenuAdapter menuAdapter;
    private List<Category> categoryList = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fabmenu=findViewById(R.id.addmenu);
        fabmenu.setVisibility(View.GONE);


        auth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);
        if (auth.getCurrentUser().getEmail().equals("administrator@gmail.com")){
            fabmenu.setVisibility(View.VISIBLE);
        }
        if(categoryList.size() >0){
            categoryList.clear();
        }


        //init firebase

        database = FirebaseDatabase.getInstance();
        //database.setPersistenceEnabled(true);
        databaseReference = database.getReference("Menu");

        dl =  findViewById(R.id.drawer_layout);
        t = new ActionBarDrawerToggle(this, dl,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dl.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nv =findViewById(R.id.nv);
        fabmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, AddCategory.class));
            }
        });

        nv.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch(id)
            {
                case R.id.nav_dashboard:
                    startActivity(new Intent(this, UserDashboard.class));
                    finish();
                    return true;
                case R.id.nav_menu:
                    startActivity(new Intent(this, Home.class));
                    finish();
                    return true;
                case R.id.nav_cart:
                    startActivity(new Intent(this, CartActivity.class));
                    finish();
                    return true;
                case R.id.nav_orders:
                    if (auth.getCurrentUser().getEmail().equals("administrator@gmail.com")){
                        startActivity(new Intent(this, EditOrders.class));
                    }else {
                    startActivity(new Intent(this, OrdersActivity.class));
                    finish();
                    }
                    return true;
                case R.id.nav_profile:
                    startActivity(new Intent(this, ProfileActivity.class));
                    finish();
                    return true;
                case R.id.nav_logout:
                    auth.signOut();
                    Intent i = new Intent(getApplicationContext(),Login.class);
                    startActivity(i);
                    finish();
                    return true;
              default:
                    return true;
            }
        });

        menuAdapter = new MenuAdapter(this, categoryList);

        //load menu
        recycler_menu = findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
//        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
     RecyclerView.LayoutManager layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recycler_menu.setLayoutManager(layoutManager);

        recycler_menu.setAdapter(menuAdapter);
//        loadMenu();


    }

    private void loadMenu() {
        showProgressDialog();

      if(categoryList.size() >0){
          categoryList.clear();
      }
      databaseReference.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              categoryList.clear();
              if(dataSnapshot.exists()){
                   for (DataSnapshot mdataSnapshot : dataSnapshot.getChildren()){
                       Category category = mdataSnapshot.getValue(Category.class);
                       categoryList.add(category);
                   }


              }
              hideProgressDialog();
              //Toast.makeText(Home.this, "" + categoryList.size(), Toast.LENGTH_SHORT).show();
              menuAdapter.notifyDataSetChanged();

          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {
              hideProgressDialog();
          }
      });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
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
    protected void onResume() {
        super.onResume();
        loadMenu();
    }
}

