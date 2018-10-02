package com.example.juan.epilepsia;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONArray;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService{
    RequestQueue request;
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        datos_prim.token = FirebaseInstanceId.getInstance().getToken();
        Log.e("nuevo", "Texto: " + datos_prim.token);
        nuevotoken();
    }
    private void nuevotoken(){
        if (datos_prim.cursor.moveToFirst())
        {
            String variable=datos_prim.cursor.getString(5).toString();
            Log.e("valor del usuario", variable);
            String URL=datos_prim.direccion+"/android/actualizar_tok.php?Token='"+datos_prim.token+"'&nombre_usu="+variable+" ";
            // prepare the Request
            JsonArrayRequest getRequest  = new JsonArrayRequest(Request.Method.GET, URL, null,
                    new Response.Listener<JSONArray>()
                    {
                        @Override
                        public void onResponse(JSONArray response) {

                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("se Error al actualizar", error.toString());
                        }
                    }
            );
            request.add(getRequest);
        }
    }
}
