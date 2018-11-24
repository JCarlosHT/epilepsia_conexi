package com.example.juan.epilepsia.ventanas;

import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.juan.epilepsia.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class mapa_ubicacion extends FragmentActivity implements OnMapReadyCallback {
    public String latitu1, longitud1,direccion1;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_ubicacion);
        latitu1=getIntent().getExtras().getString("latitud");
        longitud1=getIntent().getExtras().getString("longitud");
        direccion1=getIntent().getExtras().getString("direc");
        int status= GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if(status== ConnectionResult.SUCCESS){
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }else{
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        // Add a marker in Sydney and move the camera
        UiSettings uiSettings=mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        LatLng sydney = new LatLng(Double.parseDouble(latitu1),Double.parseDouble(longitud1));
        mMap.addMarker(new MarkerOptions().position(sydney).title("usuario"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        float zomm=16;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,zomm));
    }
}
