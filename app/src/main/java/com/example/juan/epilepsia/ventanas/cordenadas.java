package com.example.juan.epilepsia.ventanas;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.juan.epilepsia.R;
import com.example.juan.epilepsia.datos_contacto;
import com.example.juan.epilepsia.datos_prim;
import com.example.juan.epilepsia.sqlite.ConexionSQLite;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class cordenadas extends AppCompatActivity implements LocationListener {
    public String latitu, longitud,direccion;
    public LocationManager hander, locationManager;
    private String provedor;
    ArrayList<String> lista1;
    RequestQueue request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cordenadas);request= Volley.newRequestQueue(this);
        datos_contacto.sqLite= new ConexionSQLite(this);
        lista1=this.viewsqlite();
    }

    public void calculo(View view) throws UnsupportedEncodingException {
        inicio_ser();
        try {
            conexion();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void inicio_ser() {
        hander = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Criteria c = new Criteria();
        c.setAccuracy(Criteria.ACCURACY_FINE);
        provedor = hander.getBestProvider(c, false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        hander.requestLocationUpdates(provedor, 1000, 1, this);
        Location location = hander.getLastKnownLocation(provedor);
        if (location==null){
            latitu="desconocido";
            longitud="desconocido";
        }else{
            Log.e("valor",""+provedor);
            latitu=String.valueOf(location.getLatitude());
            longitud=String.valueOf(location.getLongitude());
            Direccion(location);
        }
    }

    public void Direccion(Location loc){
        if(loc.getLatitude()!=0.0&&loc.getLongitude()!=0.0){
            try {
                Geocoder geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());
                List<Address> list=geocoder.getFromLocation(loc.getLatitude(),loc.getLongitude(),1);
                if (!list.isEmpty()){
                    Address address=list.get(0);
                    direccion=address.getAddressLine(0);
                }
            }catch (IOException e){
                Log.e("valor",""+e);
            }
        }
    }

    private ArrayList viewsqlite(){
        String sql="select * from Contacto ";
        return datos_contacto.sqLite.llenarlista(sql);
    }

    public void conexion() throws UnsupportedEncodingException {
        for (int x = 0; x < lista1.size(); x++) {
            String URL = datos_prim.direccion + "/android/push.php?latitud="+latitu+"&longitud="+longitud+"&direccion="+ URLEncoder.encode(direccion,"UTF-8")+"&registration_ids="+lista1.get(x).toString();
            URL = URL.replace(" ", "%20");
            URL = URL.replace("[", "");
            URL = URL.replace("]", "");
            URL = URL.replace("\"", "");
            Log.e("valor", "" + URLEncoder.encode(direccion,"UTF-8"));
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
                    new Response.Listener <JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (null != error.networkResponse) {
                                Log.e("error" + ": ", "Error Response code: " + error.networkResponse.statusCode);
                            }
                        }
                    });
            request.add(getRequest);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
