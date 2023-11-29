package com.example.solarsports.models;

public class CatalogoTipoServicioTipoSolicitud {

    public static final String TABLE_NAME = "elemento_tpts";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ELEMENTO_ID = "elemento_id";
    public static final String COLUMN_TIPOSERVICIO_ID = "tiposervicio_id";
    public static final String COLUMN_FECHA_CREACION = "fecha_creacion";
    public static final String COLUMN_TIPOSOLICITUD_ID = "tiposolicitud_id";

    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_ELEMENTO_ID + " INTEGER," +
                    COLUMN_TIPOSERVICIO_ID + " INTEGER," +
                    COLUMN_TIPOSOLICITUD_ID + " INTEGER," +
                    COLUMN_FECHA_CREACION + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (" + COLUMN_ELEMENTO_ID + ") REFERENCES " + Elemento.TABLE_NAME + "(" + Elemento.COLUMN_ID + ")," +
                    "FOREIGN KEY (" + COLUMN_TIPOSERVICIO_ID + ") REFERENCES " + TipoServicio.TABLE_NAME + "(" + TipoServicio.COLUMN_ID + ")," +
                    "FOREIGN KEY (" + COLUMN_TIPOSOLICITUD_ID + ") REFERENCES " + TipoSolicitud.TABLE_NAME + "(" + TipoSolicitud.COLUMN_ID + ")" +
                    ")";
    private int id;
    private Elemento elemento;
    private TipoServicio tipoServicio;
    private TipoSolicitud tipoSolicitud;

    public CatalogoTipoServicioTipoSolicitud(int id, Elemento elemento, TipoServicio tipoServicio, TipoSolicitud tipoSolicitud) {
        this.id = id;
        this.elemento = elemento;
        this.tipoServicio = tipoServicio;
        this.tipoSolicitud = tipoSolicitud;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Elemento getElemento() {
        return elemento;
    }

    public void setElemento(Elemento elemento) {
        this.elemento = elemento;
    }

    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(TipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public TipoSolicitud getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(TipoSolicitud tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }
}
