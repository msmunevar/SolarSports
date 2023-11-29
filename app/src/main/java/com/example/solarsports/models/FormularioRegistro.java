package com.example.solarsports.models;

public class FormularioRegistro {

    // Definición de la tabla "formulario_registro"
    public static final String TABLE_NAME = "formulario_registro";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MES = "mes";
    public static final String COLUMN_RADIACIONRECIBIDA= "radiacion_recibida";
    public static final String COLUMN_CONSUMO = "consumo";
    public static final String COLUMN_AHORRO = "ahorro";
    public static final String COLUMN_TONELADASCO2 = "toneladasco2";
    public static final String COLUMN_DIAS_MANT = "dias_mant";
    public static final String COLUMN_DESCRIPCION = "descripcion";
    public static final String COLUMN_FECHA_CREACION = "fecha_creacion";
    public static final String COLUMN_USUARIO_ID = "usuario_id";
    public static final String COLUMN_CATALOGO_ID = "catalogo_id";

    // Sentencia CREATE TABLE para la tabla "formulario_registro"
    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_MES+ " TEXT," +
                    COLUMN_RADIACIONRECIBIDA+ " TEXT," +
                    COLUMN_CONSUMO+ " TEXT," +
                    COLUMN_AHORRO + " TEXT," +
                    COLUMN_TONELADASCO2 + " TEXT," +
                    COLUMN_DIAS_MANT +" TEXT, " +
                    COLUMN_DESCRIPCION +" TEXT, " +
                    COLUMN_FECHA_CREACION + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
                    COLUMN_USUARIO_ID + " INTEGER, " + // Agregado para la columna usuario_id
                    COLUMN_CATALOGO_ID + " INTEGER, " + // Agregado para la columna catalogo_id
                    "FOREIGN KEY (" + COLUMN_USUARIO_ID + ") REFERENCES usuarios(_id)," +
                    "FOREIGN KEY (" + COLUMN_CATALOGO_ID + ") REFERENCES " + Catalogo.TABLE_NAME + "(" + Catalogo.COLUMN_ID + ")" +
                    ")";

    private int id;
    private String mes;
    private String radiacion_recibida;
    private String consumo;
    private String ahorro;
    private String co2;
    private String dias_mant;
    private String descripcion;
    private String usuarioId;
    private String catalogoId;
    private String seccion;
    private String elemento;

    public FormularioRegistro(String seccion, String elemento, int _id) {
        this.seccion = seccion;
        this.elemento = elemento;
        this.id = _id;
    }

    // Métodos de acceso (getters) para cada variable privada
    public int getId() {
        return id;
    }

    public String getMes() {
        return mes;
    }

    public String getRadiacion_recibida() {
        return radiacion_recibida;
    }

    public String getConsumo() {
        return consumo;
    }

    public String getAhorro() {
        return ahorro;
    }

    public String getCo2() {
        return co2;
    }

    public String getDias_mant() {
        return dias_mant;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public String getCatalogoId() {
        return catalogoId;
    }

    public String getSeccion() {
        return seccion;
    }

    public String getElemento() {
        return elemento;
    }

    // Métodos de modificación (setters) para cada variable privada
    public void setId(int id) {
        this.id = id;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public void setRadiacion_recibida(String radiacion_recibida) {
        this.radiacion_recibida = radiacion_recibida;
    }

    public void setConsumo(String consumo) {
        this.consumo = consumo;
    }

    public void setAhorro(String ahorro) {
        this.ahorro = ahorro;
    }

    public void setCo2(String co2) {
        this.co2 = co2;
    }

    public void setDias_mant(String dias_mant) {
        this.dias_mant = dias_mant;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setCatalogoId(String catalogoId) {
        this.catalogoId = catalogoId;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public void setElemento(String elemento) {
        this.elemento = elemento;
    }

}
