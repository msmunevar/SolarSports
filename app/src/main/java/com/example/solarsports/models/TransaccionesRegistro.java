package com.example.solarsports.models;

public class TransaccionesRegistro {

    public static final String TABLE_NAME = "transacciones_registro";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FREGISTROID = "fregistro_id";
    public static final String COLUMN_FECHA_MODIFICACION = "fecha_modificacion";
    public static final String COLUMN_ESTADO = "estado";
    public static final String COLUMN_JUSTIFICACION = "justificacion";
    public static final String COLUMN_USUARIO_ESCALADOID = "usuario_idescalado";


    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_FREGISTROID + " INTEGER," +
                    COLUMN_FECHA_MODIFICACION + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
                    COLUMN_ESTADO +" TEXT, " +
                    COLUMN_JUSTIFICACION +" TEXT, " +
                    COLUMN_USUARIO_ESCALADOID + " INTEGER," +
                    "FOREIGN KEY (" + COLUMN_FREGISTROID + ") REFERENCES " + FormularioRegistro.TABLE_NAME + "(" + FormularioRegistro.COLUMN_ID + ")" +
                    ")";
}
