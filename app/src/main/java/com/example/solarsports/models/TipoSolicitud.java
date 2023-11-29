package com.example.solarsports.models;

public class TipoSolicitud {

    public static final String TABLE_NAME = "tiposdesolicitud";
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

    public TipoSolicitud(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
