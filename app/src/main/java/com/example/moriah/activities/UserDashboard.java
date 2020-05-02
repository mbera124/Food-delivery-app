package com.example.moriah.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.moriah.R;
import com.example.moriah.adapters.BreakfastAdapter;
import com.example.moriah.adapters.CategoryAdapter;
import com.example.moriah.adapters.LunchAdapter;
import com.example.moriah.adapters.SoftDrinksAdapter;
import com.example.moriah.admin.EditOrders;
import com.example.moriah.model.Breakfast;
import com.example.moriah.model.Category;
import com.example.moriah.model.Lunch;
import com.example.moriah.model.PrefManager;
import com.example.moriah.model.SoftDrinks;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserDashboard extends AppCompatActivity implements CategoryAdapter.onItemClicklistener {
    CardView cardViewtop, cardViewbreakfast, cardProfile, cardNotifications, cardAbout, cardSignout;
    FloatingActionButton btncart;
    PrefManager prefManager;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference databaseReference, databasebreakfastReference,databaselunchReference,databasedrinksReference ;
    RecyclerView recycler_category,recycler_breakfast;
    RecyclerView.LayoutManager layoutManager;
    private CategoryAdapter categoryAdapter;
    private BreakfastAdapter breakfastAdapter;
    private LunchAdapter lunchAdapter;
    private SoftDrinksAdapter softDrinksAdapter;
    private List<Category> categoryList = new ArrayList<>();
    private List<Breakfast> breakfastList = new ArrayList<>();
    private List<Lunch> lunchList = new ArrayList<>();
    private List<SoftDrinks> softDrinksList = new ArrayList<>();
    TextView tvname;
    ImageView imgprofile,imgedit,imgexit;
    BottomNavigationView bottomNavigationView;
    private static final String TAG = "UserDashboard";
    private static final String ARG_NAME = "username";
    int previousposition=0;

      GoogleSignInClient googleSignInClient;

      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        cardViewtop = findViewById(R.id.cardViewtop);
        cardViewbreakfast = findViewById(R.id.cardviewbreakfast);
        tvname=findViewById(R.id.tvprofilename);
        imgedit=findViewById(R.id.tvedit);
        imgexit=findViewById(R.id.tvsignout);
        imgprofile=findViewById(R.id.imgudprifile);

        imgedit.setOnClickListener(v -> {
            startActivity(new Intent(UserDashboard.this, ProfileActivity.class));
            finish();
        });
        imgexit.setOnClickListener(v -> {
            SignOut();
            startActivity(new Intent(UserDashboard.this, Login.class));
            finish();
        });

        googleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            Log.e(TAG, "Name: " + personGivenName + ", email: " + personEmail
                    + ", Image: " + personPhoto);

            tvname.setText(personGivenName);
            Glide.with(getApplicationContext()).load(personPhoto)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgprofile);
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


        prefManager = new PrefManager(this);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            Toast.makeText(UserDashboard.this, "Welcome back " + " " + acct.getDisplayName() + " ", Toast.LENGTH_LONG).show();
        }

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Category");
             //load category
        categoryAdapter = new CategoryAdapter(this, categoryList, this);
        recycler_category = findViewById(R.id.recycler_category);
        recycler_category.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false);
        recycler_category.setLayoutManager(layoutManager);
        recycler_category.setAdapter(categoryAdapter);
        loadCategory();

        databasebreakfastReference = database.getReference("Breakfast");
        databaselunchReference = database.getReference("Delights");
        databasedrinksReference = database.getReference("Softdrinks");
            //breakfast
        breakfastAdapter = new BreakfastAdapter(this, breakfastList);
        recycler_breakfast = findViewById(R.id.recycler_breakfastdash);
        recycler_breakfast.setHasFixedSize(true);
        recycler_breakfast.setNestedScrollingEnabled(false);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
//       RecyclerView.LayoutManager layoutManagerb = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
        recycler_breakfast.setLayoutManager(staggeredGridLayoutManager);
        recycler_breakfast.setAdapter(breakfastAdapter);
         loadBreakfast();


    }

    private void SignOut() {
        // Firebase sign out
        auth.signOut();
        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Google Sign In failed, update UI appropriately
                        Log.w(TAG, "Signed out of google");
                        startActivity(new Intent(UserDashboard.this, Login.class));
                        finish();
                    }
                });
    }


    private void loadBreakfast() {
        if (breakfastList.size() > 0) {
            breakfastList.clear();
        }
        databasebreakfastReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                breakfastList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot mdataSnapshot : dataSnapshot.getChildren()) {
                        Breakfast breakfast = mdataSnapshot.getValue(Breakfast.class);
                        breakfastList.add(breakfast);
                    }
                }
                breakfastAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void loadDelights() {
        if (breakfastList.size() > 0) {
            breakfastList.clear();
        }
        databaselunchReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                breakfastList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot mdataSnapshot : dataSnapshot.getChildren()) {
                        Lunch lunch = mdataSnapshot.getValue(Lunch.class);
                        Breakfast breakfast = new Breakfast();
                        breakfast.setName(lunch.getName());
                        breakfast.setPrice(lunch.getPrice());
                        breakfast.setImage(lunch.getImage());
                        breakfast.setDescription(lunch.getDescription());
                        breakfastList.add(breakfast);
                    }
                }
                breakfastAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void loadSoftDrinks() {
        if (breakfastList.size() > 0) {
            breakfastList.clear();
        }
      databasedrinksReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                breakfastList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot mdataSnapshot : dataSnapshot.getChildren()) {
                        SoftDrinks softDrinks = mdataSnapshot.getValue(SoftDrinks.class);
                        Breakfast breakfast = new Breakfast();
                        breakfast.setName(softDrinks.getName());
                        breakfast.setPrice(softDrinks.getPrice());
                        breakfast.setImage(softDrinks.getImage());
                        breakfast.setDescription(softDrinks.getDescription());
                        breakfastList.add(breakfast);
                    }
                }
                breakfastAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    private void loadCategory() {
        if (categoryList.size() > 0) {
            categoryList.clear();
        }
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                categoryList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot mdataSnapshot : dataSnapshot.getChildren()) {
                        Category category = mdataSnapshot.getValue(Category.class);
                        categoryList.add(category);
                    }
                }
                categoryAdapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onItemClick(Category category, int pos) {
        View prev=recycler_category.findViewHolderForAdapterPosition(previousposition).itemView;
        if(prev != null) {
            ImageView prevImageView = (ImageView) prev.findViewById(R.id.imgcheck);
            prevImageView.setVisibility(View.GONE);
        }
          View view=recycler_category.findViewHolderForAdapterPosition(pos).itemView;
        if(view != null){
            ImageView imageView=(ImageView) view.findViewById(R.id.imgcheck);
            imageView.setVisibility(View.VISIBLE);
        }

          previousposition = pos;

        String name = category.getName();
        switch (name.toLowerCase()){
            case "breakfast":
                 loadBreakfast();
                 break;
            case "delights":
                 loadDelights();
                 break;
            case "soft drinks":
                loadSoftDrinks();
                break;
            default:
                loadBreakfast();
        }
    }
}




