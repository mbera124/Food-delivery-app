package com.example.moriah.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moriah.R;
import com.example.moriah.adapters.CartAdapter;
import com.example.moriah.model.Common;
import com.example.moriah.model.Order;
import com.example.moriah.model.Request;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CartActivity extends AppCompatActivity{

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    TextView txttotalprice;
    Button btnplaceorder;
    private FirebaseAuth auth;
    String userName;

FirebaseDatabase database;
DatabaseReference requests;

    CartAdapter adapter;
    public static List<Order> orderList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        auth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //firebase
        String key = auth.getCurrentUser().getUid();
        database=FirebaseDatabase.getInstance();
        requests=database.getReference("Requests").child(key);
        userName =  auth.getCurrentUser().getDisplayName();

        //load menu
        recyclerView = findViewById(R.id.listcart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CartAdapter(Order.getOrderList(), this);
        recyclerView.setAdapter(adapter);

        txttotalprice=findViewById(R.id.total);
        btnplaceorder=findViewById(R.id.btnplaceorder);
btnplaceorder.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        showAlertDialog();
    }
});
        loadListFood();



    }
@Exclude
    private void showAlertDialog() {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(CartActivity.this);
        alertDialog.setTitle("One more step!");
        alertDialog.setMessage("Enter your address");
        final EditText edtaddress= new EditText(CartActivity.this);
       LinearLayout.LayoutParams  layoutParams= new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
    );
       edtaddress.setLayoutParams(layoutParams);
       alertDialog.setView(edtaddress);
        alertDialog.setIcon(R.drawable.cart);
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {

           @Override
            public void onClick(DialogInterface dialog, int which) {

               if(Order.getOrderList().size() > 0) {

                   requests = requests.child(String.valueOf(System.currentTimeMillis()));
                   String orderKey = requests.push().getKey();

                   for (Order order : Order.getOrderList()) {

                       if (!order.getProductName().equals(" ")) {

                           Request request = new Request(
                                   userName,
                                   edtaddress.getText().toString(),
                                   txttotalprice.getText().toString(),
                                   order.getProductName(),
                                   order.getUnitPrice(),
                                   order.getQuantity(),
                                   order.getTotalPrice()
                           );




                           requests.push().setValue(request);
                       }
                   }
               }




             // requests.child(String.valueOf(System.currentTimeMillis()))
                //    .setValue(request);
                orderList.clear();
                adapter.notifyDataSetChanged();
               // startActivity(new Intent(CartActivity.this, Signup.class));
               Toast.makeText(CartActivity.this, "Your order has been placed", Toast.LENGTH_SHORT).show();
               finish();
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void loadListFood() {
       //Calculate Total Price
        Double total=0.0;
        for (Order order:Order.getOrderList()) {
            //Toast.makeText(CartActivity.this, ""+ order.getProductName(), Toast.LENGTH_SHORT).show();
            if(!order.getTotalPrice().equals("")) {
                total += Double.parseDouble(order.getTotalPrice());
              //Locale locale=new Locale("en","ke");
                txttotalprice.setText(Double.toString(total));
            }
            //total=(Integer.parseInt(order.getUnitPrice()))*(Integer.parseInt(order.getQuantity()));

         //  NumberFormat fmt=NumberFormat.getCurrencyInstance(locale);


        }
    }
}
