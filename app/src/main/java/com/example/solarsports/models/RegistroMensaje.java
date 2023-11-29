package com.example.solarsports.models;

public class RegistroMensaje {

    public static final String TABLE_NAME = "registros";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FREGISTROID = "fregistro_id";
    public static final String COLUMN_USUARIOID = "nombre_id";
    public static final String COLUMN_FECHA_CREACION = "fecha_creacion";
    public static final String COLUMN_MENSAJE = "mensaje"; // Nueva columna para el mensaje

    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_FREGISTROID + " INTEGER," +
                    COLUMN_USUARIOID + " INTEGER," +
                    COLUMN_FECHA_CREACION + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
                    COLUMN_MENSAJE + " TEXT," + // Se agrega la nueva columna de tipo texto para el mensaje
                    "FOREIGN KEY(" + COLUMN_FREGISTROID + ") REFERENCES " +
                    FormularioRegistro.TABLE_NAME + "(" + Elemento.COLUMN_ID + ")," +
                    "FOREIGN KEY(" + COLUMN_USUARIOID + ") REFERENCES " +
                    "usuarios (" + Seccion.COLUMN_ID + ")" +
                    ")";

    private int id;
    private int fRegistroId;
    private int usuarioId;
    private String fechaCreacion;
    private String nombreUsuario;
    private String mensaje; // Nueva variable para el mensaje

    public RegistroMensaje(Integer id, Integer fRegistroId, Integer usuarioId, String fechaCreacion, String nombreUsuario, String mensaje) {
        this.id = id;
        this.fRegistroId = fRegistroId;
        this.usuarioId = usuarioId;
        this.fechaCreacion = fechaCreacion;
        this.nombreUsuario = nombreUsuario;
        this.mensaje = mensaje;
    }

    // Agrega aquí los getters y setters para todas las variables

    // Getter y Setter para id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter y Setter para fRegistroId
    public int getFRegistroId() {
        return fRegistroId;
    }

    public void setFRegistroId(int fRegistroId) {
        this.fRegistroId = fRegistroId;
    }

    // Getter y Setter para usuarioId
    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    // Getter y Setter para fechaCreacion
    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    // Getter y Setter para nombreUsuario
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    // Getter y Setter para mensaje
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
