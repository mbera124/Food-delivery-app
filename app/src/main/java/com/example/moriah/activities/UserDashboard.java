package com.example.moriah.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.moriah.R;
import com.example.moriah.admin.EditOrders;
import com.example.moriah.model.PrefManager;
import com.google.firebase.auth.FirebaseAuth;

public class UserDashboard extends AppCompatActivity {
    CardView cardMenu, cardOrders, cardProfile, cardNotifications, cardAbout, cardSignout;
    PrefManager prefManager;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        cardMenu = findViewById(R.id.cardMenu);
        cardOrders= findViewById(R.id.cardOrders);
        cardProfile = findViewById(R.id.cardProfile);
        cardNotifications = findViewById(R.id.cardNotifications);
        cardAbout = findViewById(R.id.cardAbout);
        cardSignout = findViewById(R.id.cardsignout);

        prefManager = new PrefManager(this);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            Toast.makeText(UserDashboard.this, "Welcome back "+" "+ prefManager.getUserName()+" ", Toast.LENGTH_LONG).show();
        }

          cardMenu.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  cardMenuClicked();
              }
          });
        cardOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardOrdersClicked();
            }
        });

        cardProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardProfileClicked();
            }
        });

        cardNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardNotificationsClicked();
            }
        });

        cardAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardAboutClicked();
            }
        });
        cardSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardSignoutClicked();
            }
        });
    }

    public void  cardMenuClicked() {

            cardMenu.setCardElevation(15);
            // cardHomes.animate().rotationBy(360);
            cardMenu.animate().alphaBy(10);
            cardMenu.setRadius(20);
            cardMenu.setPadding(2, 2, 2, 2);
            cardOrders.setCardElevation(0);
            cardOrders.setRadius(0);
            cardProfile.setCardElevation(0);
            cardProfile.setRadius(0);
            cardNotifications.setCardElevation(0);
            cardNotifications.setRadius(0);
            cardSignout.setCardElevation(0);
            cardSignout.setRadius(0);
            cardAbout.setCardElevation(0);
            cardAbout.setRadius(0);

            startActivity(new Intent(UserDashboard.this, Home.class));
    }

    public void cardOrdersClicked() {
            cardOrders.setCardElevation(15);
            cardOrders.animate().rotationBy(360);
            cardOrders.setRadius(20);
            cardMenu.setCardElevation(0);
            cardMenu.setRadius(0);
            cardProfile.setCardElevation(0);
            cardProfile.setRadius(0);
            cardNotifications.setCardElevation(0);
            cardNotifications.setRadius(0);
            cardSignout.setCardElevation(0);
            cardSignout.setRadius(0);
            cardAbout.setCardElevation(0);
            cardAbout.setRadius(0);
        if (auth.getCurrentUser().getEmail().equals("administrator@gmail.com")){
            Intent i = new Intent(getApplicationContext(), EditOrders.class);
            startActivity(i);
        }else {
            Intent i = new Intent(getApplicationContext(), OrdersActivity.class);
            startActivity(i);
        }
    }

    public void cardProfileClicked() {
            cardProfile.setCardElevation(15);
            // cardApartments.animate().rotationBy(360);
            cardProfile.animate().alphaBy(10);
            cardProfile.setRadius(20);
            cardMenu.setCardElevation(0);
            cardMenu.setRadius(0);
            cardOrders.setCardElevation(0);
            cardOrders.setRadius(0);
            cardNotifications.setCardElevation(0);
            cardNotifications.setRadius(0);
            cardSignout.setCardElevation(0);
            cardSignout.setRadius(0);
            cardAbout.setCardElevation(0);
            cardAbout.setRadius(0);

            Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(i);


            //startActivity(new Intent(UserDashboardActivity.this, Apartments.class));


    }

    public void cardNotificationsClicked() {
            cardNotifications.setCardElevation(15);
            // cardSales.animate().rotationBy(360);
            cardNotifications.animate().alphaBy(10);
            cardNotifications.setRadius(20);
            cardMenu.setCardElevation(0);
            cardMenu.setRadius(0);
            cardOrders.setCardElevation(0);
            cardOrders.setRadius(0);
            cardProfile.setCardElevation(0);
            cardProfile.setRadius(0);
            cardSignout.setCardElevation(0);
            cardSignout.setRadius(0);
            cardAbout.setCardElevation(0);
            cardAbout.setRadius(0);
            Intent i = new Intent(getApplicationContext(),NotificationsActivity.class);
            startActivity(i);

    }
    public void cardSignoutClicked() {
            cardSignout.setCardElevation(15);
            //cardSignout.animate().rotationBy(360);
            cardSignout.animate().alphaBy(10);
            cardSignout.setRadius(20);
            cardMenu.setCardElevation(0);
            cardMenu.setRadius(0);
            cardOrders.setCardElevation(0);
            cardOrders.setRadius(0);
            cardProfile.setCardElevation(0);
            cardProfile.setRadius(0);
            cardNotifications.setCardElevation(0);
            cardNotifications.setRadius(0);
            cardAbout.setCardElevation(0);
            cardAbout.setRadius(0);
             auth.signOut();
            Intent i = new Intent(getApplicationContext(),Login.class);
            startActivity(i);
    }


    public void cardAboutClicked() {
            cardAbout.setCardElevation(15);
            //cardAbout.animate().rotationBy(360);
            cardAbout.animate().alphaBy(10);
            cardAbout.setRadius(20);
            cardMenu.setCardElevation(0);
            cardMenu.setRadius(0);
            cardOrders.setCardElevation(0);
            cardOrders.setRadius(0);
            cardProfile.setCardElevation(0);
            cardProfile.setRadius(0);
            cardNotifications.setCardElevation(0);
            cardNotifications.setRadius(0);
            cardSignout.setCardElevation(0);
            cardSignout.setRadius(0);
            Intent i = new Intent(getApplicationContext(), AboutUsActivity.class);
            startActivity(i);

    }

}
