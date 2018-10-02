package com.example.juan.epilepsia.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ConexionSQLite{
    Context context;
    private Helper helper;
    private SQLiteDatabase bd;
    String nombre_base = "bd";// nombre de la base de datos
    int version = 1;
    String[] tablas = {"create table usuario (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre text, apellido_pater text, apellido_mater text, correo text,Us_nom text, Contra text, token text)",
            "create table Contacto (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre_cont text, correo text, token text)"};///Aqui van todas las tablas a crear,
    // en caso de que se les olvide agregar una desinstalar la aplicacion y volverla a ejecutar para cargar todas las tablas
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

    public ArrayList llenarlista(String sql)// consultas select * from .....
    {
        ArrayList lista=new ArrayList();
        try {
            this.Abrir();
            Cursor c = bd.rawQuery(sql,null);
            if(c.moveToFirst()){
                do {
                    lista.add(c.getString(3));
                }while (c.moveToNext());
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