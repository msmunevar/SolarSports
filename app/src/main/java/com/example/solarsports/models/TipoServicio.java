package com.example.solarsports.models;

import java.util.List;

public class TipoServicio {
    public static final String TABLE_NAME = "tiposdeservicio";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_FECHA_CREACION = "fecha_creacion";
    // Agrega más columnas según tus necesidades

    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NOMBRE + " TEXT" +
                    COLUMN_FECHA_CREACION + " DATETIME DEFAULT CURRENT_TIMESTAMP" +
                    // Agrega más columnas a la sentencia CREATE TABLE según tus necesidades
                    ")";
    private int id;
    private String nombre;
    private List<TipoSolicitud> tiposSolicitud;

    public TipoServicio(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.tiposSolicitud = tiposSolicitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<TipoSolicitud> getTiposSolicitud() {
        return tiposSolicitud;
    }

    public void setTiposSolicitud(List<TipoSolicitud> tiposSolicitud) {
        this.tiposSolicitud = tiposSolicitud;
    }

    // Otros métodos, si es necesario
}
