package com.example.solarsports.models;

import java.util.List;

public class Elemento {
    public static final String TABLE_NAME = "elementos";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_FECHA_CREACION = "fecha_creacion";

    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NOMBRE + " TEXT," +
                    COLUMN_FECHA_CREACION + " DATETIME DEFAULT CURRENT_TIMESTAMP" +
                    // Add more columns to the CREATE TABLE query as needed
                    ")";

    private int id;
    private String nombre;
    private String fechaCreacion;
    private String tiempoSLA;
    private int idItemSeccion;
    private List<TipoServicio> tiposServicio;

    public Elemento(String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and Setter for nombre
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Getter and Setter for tiposServicio
    public List<TipoServicio> getTiposServicio() {
        return tiposServicio;
    }

    public void setTiposServicio(List<TipoServicio> tiposServicio) {
        this.tiposServicio = tiposServicio;
    }

    // Getter para idItemSeccion
    public int getIdItemSeccion() {
        return idItemSeccion;
    }

    // Setter para idItemSeccion
    public void setIdItemSeccion(int idItemSeccion) {
        this.idItemSeccion = idItemSeccion;
    }
    // ... (existing getters and setters)

    public void agregarTipoServicio(TipoServicio tipoServicio) {
        tiposServicio.add(tipoServicio);
    }
    @Override
    public String toString() {
        return nombre; // Este toString lo utiliza el adpater.
    }

}


