package com.example.juan.epilepsia;

import java.io.Serializable;

public class datos_alerta_repo implements Serializable {
    public static String latitud;
    public static String longitud;
    public static String Direccion;
    int bandera;

    public datos_alerta_repo() {
        bandera=0;
    }
}
