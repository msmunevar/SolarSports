package com.example.solarsports.models;

import com.example.solarsports.models.Elemento;

import java.util.ArrayList;
import java.util.List;

public class Seccion {
    public static final String TABLE_NAME = "secciones";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_FECHA_CREACION = "fecha_creacion"; // New column

    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NOMBRE + " TEXT," +
                    COLUMN_FECHA_CREACION + " DATETIME DEFAULT CURRENT_TIMESTAMP" + // Include the new column in the CREATE TABLE query
                    ")";
    private String nombre;
    private String fechaCreacion; // New variable
    private List<Elemento> elementos;

    public Seccion(String nombre) {
        this.nombre = nombre;
        this.elementos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<Elemento> getElementos() {
        return elementos;
    }
}
