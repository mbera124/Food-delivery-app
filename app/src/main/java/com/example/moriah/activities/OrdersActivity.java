package com.example.moriah.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import android.os.Bundle;

import com.example.moriah.R;
import com.example.moriah.model.Request;
import com.example.moriah.viewholders.OrdersViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrdersActivity extends AppCompatActivity {
public RecyclerView recyclerView;
public LayoutManager layoutManager;
    private FirebaseAuth auth;

FirebaseRecyclerAdapter<Request, OrdersViewHolder> adapter;
FirebaseDatabase database;
DatabaseReference requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        auth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        requests=database.getReference("Requests").child(auth.getUid());

        //load menu
        recyclerView = findViewById(R.id.listorders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView .setLayoutManager(layoutManager);
        recyclerView .setAdapter(adapter);
        loadOrders(auth.getCurrentUser().getDisplayName());

    }

    private void loadOrders(String displayName) {
     adapter=new FirebaseRecyclerAdapter<Request, OrdersViewHolder>(
             Request.class,
             R.layout.order_layout,
             OrdersViewHolder.class,
             requests.orderByChild("status")
             //.equalTo(displayName)

     )
        {
         @Override
         protected void populateViewHolder(OrdersViewHolder ordersViewHolder, Request request, int i) {
             ordersViewHolder.txtorderid.setText(adapter.getRef(i).getKey());
             ordersViewHolder.txtorderstatus.setText(convertCodeToStatus(request.getStatus()));
             ordersViewHolder.txtorderaddress.setText(request.getAddress());
             ordersViewHolder.txtordername.setText(request.getName());
         }
     };
     recyclerView.setAdapter(adapter);

    }

    private String convertCodeToStatus(String status) {
     if (status.equals("0"))
         return "placed";
     else if (status.equals("1"))
         return "on the way";
      else
          return "shipped";
    }
}
