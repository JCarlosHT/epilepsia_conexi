package com.example.juan.epilepsia.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class ConexionSQLite{
    Context context;
    private Helper helper;
    private SQLiteDatabase bd;
    String nombre_base = "bd";// nombre de la base de datos
    int version = 1;
    String[] tablas = {"create table usuario (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre text, apellido_pater text, apellido_mater text, correo text,Us_nom text, Contra text, token text)",
                        "create table Contacto_da (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre text, apellido_pater text, apellido_mater text, correo text,Us_nom text, Contra text, token text)",
                        "create table Contacto (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre_cont text, correo text, token text,nombre text,apellido text,relacion text)",
                        "create table Datos_medi (id INTEGER PRIMARY KEY AUTOINCREMENT, Edad int, sexo text, tipo_san text, hospital_pre text)",
                        "create table Recordatorio (id INTEGER PRIMARY KEY AUTOINCREMENT, medicina text, dosis text, fecha text, hora text, otros text)",
                        "create table Alarma (id INTEGER PRIMARY KEY AUTOINCREMENT, direccion text, sin_previos text, ti_ataque text,grad_ata text,fecha text, hora text, otros text)"};///Aqui van todas las tablas a crear,
    // para hacer modificaciones a la base de datos borrar la aplicacion e instalar nuevamente
    public ConexionSQLite(Context context)
    {
        this.context = context;
    }

    private void Abrir() throws SQLiteException //Abrir la base de datos
    {
        helper = new Helper(this.context);
        bd = helper.getWritableDatabase();
    }

    private void Cerrar() //Cerrar la base de datos
    {
        helper.close();
    }

    public boolean ejecutaSQL(String sql) // ejecutar insert, update, delete
    {
        try {
            this.Abrir();
            bd.execSQL(sql);
            this.Cerrar();
            return true;
        }catch (SQLiteException s)
        {
            return false;
        }
    }

    public Cursor consultaSQL(String sql)// consultas select * from .....
    {

        try {
            this.Abrir();
            Cursor c = bd.rawQuery(sql,null);
            return c;
        }catch (SQLiteException s)
        {
            this.Cerrar();
            return null;
        }
    }

    public ArrayList llenarlista(String sql, int bandera)// consultas select * from para llenar las listas de las diferentes clases
    {
        ArrayList lista=new ArrayList();
        try {
            this.Abrir();
            Cursor c = bd.rawQuery(sql,null);
            if(c.moveToFirst()){
                String datos="";
                if (bandera==1){
                    do {
                        lista.add(c.getString(3));
                    }while (c.moveToNext());
                }else if (bandera==2){
                    do {

                        datos=c.getString(0)+ " "+c.getString(4)+ " "+c.getString(5)+ " "+c.getString(6);
                        lista.add(datos);
                        Log.e("valor", "" + datos);
                    }while (c.moveToNext());
                }else if (bandera==3){

                    do {
                        datos=c.getString(1)+ " "+c.getString(2)+ " "+c.getString(3);
                        lista.add(datos);
                        lista.add(c.getString(5));
                        lista.add(c.getString(4));
                    }while (c.moveToNext());
                }else if (bandera==4){
                    do {
                         lista.add(c.getString(4));/*
                        lista.add(c.getString(5));
                        lista.add(c.getString(4));*/
                    }while (c.moveToNext());
                }else if (bandera==5){
                    do {
                        datos=c.getString(0)+" "+c.getString(1)+" "+c.getString(2)+" "+c.getString(3);
                        Log.e("cadena",""+datos);
                        lista.add(datos);/*
                        lista.add(c.getString(5));
                        lista.add(c.getString(4));*/
                    }while (c.moveToNext());
                }if (bandera==6){

                    do {
                        datos=c.getString(1)+ " "+c.getString(2)+ " "+c.getString(3);
                        lista.add(datos);
                        lista.add(c.getString(6));
                        lista.add(c.getString(4));
                    }while (c.moveToNext());
                }
            }else{
                lista.add("0");
            }
            return lista;
        }catch (SQLiteException s)
        {
            this.Cerrar();
            return null;
        }

    }

    public ArrayList llenarlista_alar(String sql, int bandera)// consultas select * from para llenar las listas de las diferentes clases
    {
        ArrayList lista=new ArrayList();
        try {
            this.Abrir();
            Cursor c = bd.rawQuery(sql,null);
            if(c.moveToFirst()){
                String datos="";
                if (bandera==1){
                    do {
                        lista.add(c.getString(1));
                        lista.add(c.getString(2));
                        lista.add(c.getString(3));
                        lista.add(c.getString(4));
                        lista.add(c.getString(5));
                    }while (c.moveToNext());
                }else if (bandera==2){
                    do {
                        lista.add(c.getString(0));
                        lista.add(c.getString(1));
                        lista.add(c.getString(2));
                        lista.add(c.getString(3));
                        lista.add(c.getString(4));
                        lista.add(c.getString(5));
                    }while (c.moveToNext());
                }else if (bandera==3){

                    do {
                        datos=c.getString(1)+ " "+c.getString(2)+ " "+c.getString(3);
                        lista.add(datos);
                        lista.add(c.getString(5));
                        lista.add(c.getString(4));
                    }while (c.moveToNext());
                }else if (bandera==4){
                    do {
                        lista.add(c.getString(4));/*
                        lista.add(c.getString(5));
                        lista.add(c.getString(4));*/
                    }while (c.moveToNext());
                }else if (bandera==5){
                    do {
                        datos=c.getString(0)+" "+c.getString(1)+" "+c.getString(2)+" "+c.getString(3);
                        Log.e("cadena",""+datos);
                        lista.add(datos);/*
                        lista.add(c.getString(5));
                        lista.add(c.getString(4));*/
                    }while (c.moveToNext());
                }
            }else{
                lista.add("0");
            }
            return lista;
        }catch (SQLiteException s)
        {
            this.Cerrar();
            return null;
        }

    }

    private class Helper extends SQLiteOpenHelper {
        public Helper(Context context) {
            super(context, nombre_base, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase bd) {
            for(int i = 0; i < tablas.length; i++)
                bd.execSQL(tablas[i]); //Ejecuta las tablas
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}
