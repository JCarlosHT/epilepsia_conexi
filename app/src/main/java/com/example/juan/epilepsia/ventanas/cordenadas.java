package com.example.juan.epilepsia.ventanas;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
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
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
 import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.juan.epilepsia.R;
import com.example.juan.epilepsia.conexion_server.Conexion;
import com.example.juan.epilepsia.datos_contacto;
import com.example.juan.epilepsia.datos_prim;
import com.example.juan.epilepsia.sqlite.ConexionSQLite;

import org.json.JSONArray;
import org.json.JSONException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.widget.Toast.LENGTH_LONG;

public class cordenadas extends AppCompatActivity implements LocationListener{//implementacion para conseguir la ubicacion
    private EditText []datos1=new EditText[5];//un arreglo que se ocupa para los textos de la interface
    private String []datos=new String[5];
    public String latitu, longitud,direccion;//son las variables para optener la ubicacion
    public LocationManager hander;
    private String provedor;
    ArrayList<String> lista1;
    RequestQueue request;
    int bande=0,bandera2;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle datos = this.getIntent().getExtras();
        bande=datos.getInt("bande");//se resibe una bandera de otro intent
        bandera2=datos.getInt("validar");
        request= Volley.newRequestQueue(this);
        datos_contacto.sqLite= new ConexionSQLite(this);
        if(bande==1){//se hace una validacion para entrar en instrucciones posteriores
            lista1=this.viewsqlite();//se rellena la lista
            iniciar_servi();
            Toast.makeText(getApplicationContext(), "Alerta enviada", LENGTH_LONG).show();
            startActivity(new Intent(getBaseContext(),menu_epi.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
        }else{
            setContentView(R.layout.activity_cordenadas);
        }
    }
    //se agregan los datos para ser validados despues, se agregan al arrglo de edittex
    private void validar(){
        datos1[0]= (EditText) findViewById(R.id.user_contacto);
        datos1[1]= (EditText) findViewById(R.id.correo_con);
        datos1[2]= (EditText) findViewById(R.id.Nombre_contac);
        datos1[3]= (EditText) findViewById(R.id.Apellido_con);
        datos1[4]= (EditText) findViewById(R.id.Relacion_cont);
        for (int i=0;i<5;i++){//se usa un for para rellenar los datos de manera mas rapida
            datos[i]=datos1[i].getText().toString();
        }
    }

    //se validan los datos que no esten vacios
    public void Agregar_usu(View view){
        validar();
        if ((datos[0].equals(""))||(datos[1].equals(""))||(datos[2].equals(""))||(datos[3].equals(""))||(datos[4].equals("")))
        {
            Toast.makeText(getApplicationContext(), "faltan datos", LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "datos del usuario:"+datos_prim.Us_nom, LENGTH_LONG).show();
            Log.e("valor","datos:"+datos_prim.Us_nom);
        }else
        {
            if(bandera2==0){
                conexion();
            }else{
                datos_prim.cursor=this.viewsqlite2();
                if(datos_prim.cursor.getCount()>0){//se verifica que existan datos en la base de datos sqlit
                    Toast.makeText(getApplicationContext(), "El usuario ya esta registrado", LENGTH_LONG).show();
                }else{
                    conexion();
                }

            }
        }
    }

    private Cursor viewsqlite2(){//se indica que seleccione el primer registro existente
        String sql="select * from Contacto where nombre_cont='"+datos[0]+"' or correo='"+datos[1]+"';";
            Log.e("consulta",""+sql);
            return datos_contacto.sqLite.consultaSQL(sql);
    }

    /////se conecta con el servidor para verificar que exista el usuario y luego manda a sqlite
    public void conexion(){
        datos_contacto.sqLite=new ConexionSQLite(this);
        Log.e("valor",""+datos[0]);
        Log.e("valor",""+datos[1]);
        String URL= datos_prim.direccion+"/android/consulta.php?nombre="+datos[0]+"&correo="+datos[1]+"&usu_epi="+datos_prim.Us_nom;//manda los datos para ver la existencia del susuario
        //tambien se manda el usuario y se selecciona para agregar a la tabla usuario_contacto de MYSQL
        URL=URL.replace(" ","%20");
        // prepare the Request
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length()>0&&!(response.equals(null))){///si los datos del contacto existen se manda a gregar en caso de ser nulos manda a que no existe
                    try {
                        JSONArray ja = new JSONArray(response);//se espera la respuesta de que viene en un arreglo con el token del usuario
                        Log.e("valor",""+ja.toString());
                        datos_contacto.token=ja.toString();
                        boolean o=addusuario();
                        Log.e("valor",""+o);
                        if(o){
                            Toast.makeText(getApplicationContext(), "Contacto agregado", LENGTH_LONG).show();
                            Intent menu = new Intent(getApplicationContext(),alarma.class);
                            menu.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(menu);
                        }

                    } catch (JSONException e) {///en caso de que no se encuentre el usuario se manda este error
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "El usuario o el correo son incorrectos", LENGTH_LONG).show();
                    }
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);
    }

    ///metodo para agregar en la base de datos sqlite los contactos
    private boolean addusuario(){
        String sql="insert into Contacto (nombre_cont,correo,token,nombre,apellido,relacion)values('"+datos[0]+"','"+datos[1]+"','"+datos_contacto.token+"','"+datos[2]+"','"+datos[3]+"','"+datos[4]+"');";
        Log.e("valor","completado ");
        return datos_contacto.sqLite.ejecutaSQL(sql);
    }

    public void  iniciar_servi(){
        do {
            inicio_ser();//es el servicio de localizacion
            Log.e("valor","va");
        }while ((latitu.equals("null")&&longitud.equals("null")));//mientras los dos sean diferentes de nulo puede salir
        try {
            Enviar_alar();//se mandan los datos optenidos
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }//se manda a llamar el servicio
    //servicio para calculo de posicion
    public void inicio_ser() {
        hander = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Criteria c = new Criteria();
        c.setAccuracy(Criteria.ACCURACY_FINE);
        provedor = hander.getBestProvider(c, false);
        //metodo para aclos servicios de localizacion linea 165
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
        hander.requestLocationUpdates(provedor, 1000, 1, this);//cada cuantos se tiene que estar mandando a llamar el servicio
        Location location = hander.getLastKnownLocation(provedor);
        if (location==null){//en caso de ser nulo se le especifica
            latitu="null";
            longitud="null";
        }else{//se optienen los datos y se castean a un dato tipo flotante
            latitu=String.valueOf(location.getLatitude());
            longitud=String.valueOf(location.getLongitude());
            Log.e("valor",""+latitu);
            Direccion(location);//este es para calcular la ubicacion con una direccion, los anteriores son dato de longitud y latitud
        }
    }

    //se optien
    public void Direccion(Location loc){
        if(loc.getLatitude()!=0.0&&loc.getLongitude()!=0.0){//se verifican que sean diferentes de 0
            try {
                Geocoder geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());//se ocupan la latitud y long para calcular con un servicio de google
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
        String sql="select * from Contacto";//se crea la instruccion y se retorna una lista
        return datos_contacto.sqLite.llenarlista(sql,1);
    }

    ///todos los datos se envian a la clase Conexion y luego se envian al servicio fire base
    public void Enviar_alar() throws UnsupportedEncodingException {
        Conexion con=new Conexion();
        con.lista1=lista1;
        con.latitu=latitu;
        con.longitud=longitud;
        con.direccion=direccion;
        con.inicio(getBaseContext());
        con.bandera=1;
    }


    //metodos que no se usan pero se deben importar
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
