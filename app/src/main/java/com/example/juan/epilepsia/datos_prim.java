package com.example.juan.epilepsia;

import android.database.Cursor;

import com.example.juan.epilepsia.sqlite.ConexionSQLite;

import java.io.Serializable;

public class datos_prim implements Serializable{
    public static String direccion="http://192.168.1.68";
    public static ConexionSQLite sqLite=null;
    public static String token;
    public static Cursor cursor=null;
    private String nombre;
    private String apelli_pater;
    private String apelli_mater;
    private String correo;
    public static String Us_nom;
    private String contra;
    private String sexo;
    private int edad;
    private String sangre;
    private String hospital;


    public datos_prim() {
        this.nombre = "";
        this.apelli_pater = "";
        this.apelli_mater = "";
        this.correo = "";
        sexo="";
        Us_nom = "";
        this.contra = "";
        this.edad = 0;
        this.sangre = "";
        this.hospital = "";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApelli_pater() {
        return apelli_pater;
    }

    public void setApelli_pater(String apelli_pater) {
        this.apelli_pater = apelli_pater;
    }

    public String getApelli_mater() {
        return apelli_mater;
    }

    public void setApelli_mater(String apelli_mater) {
        this.apelli_mater = apelli_mater;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getSangre() {
        return sangre;
    }

    public void setSangre(String sangre) {
        this.sangre = sangre;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }
    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }


}
