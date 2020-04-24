package com.example.moriah.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.example.moriah.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

//import android.location.LocationListener;

public class TrackOrder  extends FragmentActivity implements OnMapReadyCallback {
        private GoogleMap googleMap;

    private double lat=0.0, lon=0.0;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_track_order);

            if(getIntent().getExtras() != null){
                Intent intent = getIntent();
                lat = intent.getDoubleExtra("latitude",-1.2867733);
                lon = intent.getDoubleExtra("longitude", 37.0532831);
            }

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

        }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        double latitude=lat;
        double longitude=lon;
        //add marker
        LatLng yourLocation=new LatLng(latitude,longitude);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(new MarkerOptions().position(yourLocation).title("User Location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(yourLocation));
//        googleMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
//        googleMap.setMyLocationEnabled(true);
//        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);

    }


}
