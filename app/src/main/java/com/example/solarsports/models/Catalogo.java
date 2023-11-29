package com.example.solarsports.models;

import java.util.ArrayList;
import java.util.List;

public class Catalogo {
    public static final String TABLE_NAME = "item_seccion";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ITEM_ID = "item_id";
    public static final String COLUMN_SECCION_ID = "seccion_id";
    public static final String COLUMN_USUARIOID_RESPONSABLE = "usuarioid_responsable"; // New column
    public static final String COLUMN_USUARIOID_CREADOR = "usuarioid_creador";
    public static final String COLUMN_DESCRIPCION = "descripcion"; // New column
    public static final String COLUMN_FECHA_CREACION = "fecha_creacion"; // New column
    public static final String COLUMN_SLA = "sla"; // New column

    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_ITEM_ID + " INTEGER," +
                    COLUMN_SECCION_ID + " INTEGER," +
                    COLUMN_USUARIOID_RESPONSABLE + " INTEGER," + // Include the new columns in the CREATE TABLE query
                    COLUMN_USUARIOID_CREADOR + " INTEGER," +
                    COLUMN_DESCRIPCION + " TEXT," +
                    COLUMN_FECHA_CREACION + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
                    COLUMN_SLA + " TEXT," +
                    "FOREIGN KEY(" + COLUMN_ITEM_ID + ") REFERENCES " +
                    Elemento.TABLE_NAME + "(" + Elemento.COLUMN_ID + ")," +
                    "FOREIGN KEY(" + COLUMN_SECCION_ID + ") REFERENCES " +
                    Seccion.TABLE_NAME + "(" + Seccion.COLUMN_ID + ")" +
                    ")";
    private int id; // Agregar el campo id

    private List<Seccion> secciones;

    public Catalogo(int id) {
        this.id = id;
        this.secciones = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Seccion> getSecciones() {
        return secciones;
    }
}

