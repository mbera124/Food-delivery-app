package com.example.moriah.viewholders;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moriah.Interface.ItemClickListener;
import com.example.moriah.R;
import com.example.moriah.activities.OrderedActivity;
import com.example.moriah.activities.OrdersActivity;
import com.example.moriah.activities.TrackOrder;
import com.example.moriah.adapters.EditOrderAdapter;
import com.example.moriah.adapters.OrdersAdapter;
import com.example.moriah.model.Order;
import com.example.moriah.model.Request;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class OrdersViewHolder  extends RecyclerView.ViewHolder {
    public TextView txtorderid, txtorderstatus, txtorderprice, txtordercontact, txtorderLocation,txtname;
    public ImageView imageView,tick,telephone,road,motorcycle;
    public CardView cvorderitem;
    private FirebaseAuth auth;
    private List<Order> orderList;
    NotificationManager mNotificationManager;
    public ItemClickListener itemClickListener;
    private static final int NOTIFY_ME_ID=1337;

    public OrdersViewHolder(@NonNull View itemView) {
        super(itemView);
        txtorderid = itemView.findViewById(R.id.order_id);
        txtorderstatus = itemView.findViewById(R.id.order_status);
        txtorderprice = itemView.findViewById(R.id.order_price);
//        txtordercontact = itemView.findViewById(R.id.order_contact);
//        txtorderLocation = itemView.findViewById(R.id.order_location);
        imageView = itemView.findViewById(R.id.imgorder);
        tick = itemView.findViewById(R.id.status);
        telephone = itemView.findViewById(R.id.contact);
        road = itemView.findViewById(R.id.location);
       motorcycle = itemView.findViewById(R.id.motorcycle);
        cvorderitem = itemView.findViewById(R.id.cvorderlayout);
//        txtname=itemView.findViewById(R.id.user_name);
    }

    public void bind(final Request request, OrdersAdapter.onItemClicklistener onItemClick) {
        String currInit = "Ksh. ";
        txtorderprice.setText(currInit+request.getTotal());
        txtorderid.setText(request.getProductId());
        txtorderstatus.setText(convertToStatus(request.getStatus()));
//        txtordercontact.setText(request.getContact());


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(itemView.getContext(), OrderedActivity.class);
                intent.putExtra("orderId", request.getProductId());
                itemView.getContext().startActivity(intent);
            }
        });
        GeocodingLocation locationAddress = new GeocodingLocation();

        String latitude="", longitude ="";
        String[] locationarray = request.getTxtLocationResult().split(",");
        latitude=locationarray[0];
        longitude = locationarray[1];
        double lat, lon;
        lat = Double.parseDouble(latitude);
        lon  =Double.parseDouble(longitude);

        getAddressFromLocation(lat, lon, itemView.getContext(), locationAddress);
        road.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onItemClick.onItemClick(request);
                Intent intent = new Intent(itemView.getContext(), TrackOrder.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lon);
                itemView.getContext().startActivity(intent);

            }
        });

    }

    private String convertToStatus(String status) {
        if(status != null) {
            if (status.toLowerCase().equals("placed")){
                tick.setVisibility(View.GONE);
                motorcycle.setVisibility(View.GONE);
                return "placed";}
            else if (status.toLowerCase().equals("received")) {
                tick.setVisibility(View.GONE);
                motorcycle.setVisibility(View.GONE);
                return "received";
            }

            else if (status.toLowerCase().equals("on the way")) {
                tick.setVisibility(View.GONE);
                motorcycle.setVisibility(View.VISIBLE);
                notifymessage();
                return "on the way";
            }
            else if(status.toLowerCase().equals("shipped")){
                tick.setVisibility(View.VISIBLE);
                motorcycle.setVisibility(View.GONE);
                return "shipped";
            }

            else {
                return "placed";
            }
        }
        else{
            return "placed";
        }

    }

    private void notifymessage() {
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(itemView.getContext());
        nBuilder.setContentTitle("MORIAH");
        nBuilder.setContentText("Your order has been processed");
        nBuilder.setTicker("Moriah Notification");
        nBuilder.setAutoCancel(true);
        nBuilder.setSmallIcon(R.drawable.bell);
        Intent intent = new Intent(itemView.getContext(), OrdersActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(itemView.getContext());
        stackBuilder.addParentStack(OrdersActivity.class);
        stackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);
        nBuilder.setContentIntent(pendingIntent);
        mNotificationManager =(NotificationManager) itemView.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFY_ME_ID, nBuilder.build());
    }

    public void bind(final Request request, EditOrderAdapter.onItemClicklistener onItemClick) {
        txtorderprice.setText(request.getTotal());
        txtorderid.setText(request.getUserIdkey());
//        txtname.setText(request.getName());
        txtorderstatus.setText(convertToStatus(request.getStatus()));
//        txtordercontact.setText(request.getContact());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(itemView.getContext(), OrderedActivity.class);
                intent.putExtra("orderId", request.getProductId());
                itemView.getContext().startActivity(intent);
            }
        });
        GeocodingLocation locationAddress = new GeocodingLocation();

        String latitude="", longitude ="";
        String[] locationarray = request.getTxtLocationResult().split(",");
        latitude=locationarray[0];
        longitude = locationarray[1];
        double lat, lon;
        lat = Double.parseDouble(latitude);
        lon  =Double.parseDouble(longitude);
       // Handler handler = new Handler();
        getAddressFromLocation(lat, lon, itemView.getContext(), locationAddress);
        road.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(itemView.getContext(), TrackOrder.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lon);
                itemView.getContext().startActivity(intent);

            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(itemView.getContext(), OrderedActivity.class);
                intent.putExtra("orderId", request.getProductId());
                itemView.getContext().startActivity(intent);
            }
        });
        txtorderstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(request);
            }
        });


    }

    private class GeocodingLocation extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
//            txtorderLocation.setText(locationAddress);
        }
    }


    public  void getAddressFromLocation(final double latitude, final double longitude, final Context context, final Handler handler) {
       // final Handler handler = new Handler();
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                try {
                    List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                            sb.append(address.getAddressLine(i)); //.append("\n");
                        }
                        sb.append(address.getLocality()).append(",");
                        sb.append(address.getSubLocality()).append(",");
                        sb.append(address.getCountryName());
                        result = sb.toString();
                    }
                } catch (IOException e) {
                    Log.e("Location Address Loader", "Unable connect to Geocoder", e);
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (result != null) {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putString("address", result);
                        message.setData(bundle);
                        Log.d("MyLocation", result);
                       // txtorderLocation.setText(result);
                    } else {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        result = " Unable to get address for this location.";
                        bundle.putString("address", result);
                        message.setData(bundle);
                        Log.d("MyLocation", result);
                       // txtorderLocation.setText(result);
                    }
                    message.sendToTarget();
                }
            }
        };
        thread.start();
    }


}







