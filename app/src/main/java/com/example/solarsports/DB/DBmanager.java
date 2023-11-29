package com.example.solarsports.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;

//import com.example.mesaservicios.models.FormularioRegistro;

import java.util.Date;

public class DBmanager {
    public static final String TABLA_USUARIOS = "usuarios";
    public static final String USUARIO_ID = "_id";
    public static final String USUARIO = "usuario";
    public static final String CONTRASENA = "contrasena";
    public static final String ROL = "rol";
    public static final String TABLA_USUARIOS_CREATE = "CREATE TABLE " + TABLA_USUARIOS + " (" +
            USUARIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            USUARIO + " TEXT NOT NULL, " +
            CONTRASENA + " TEXT NOT NULL, " +
            ROL + " TEXT NOT NULL);";

    private DBconexion _conexion;
    private SQLiteDatabase _basededatos;

    public DBmanager(Context context) {
        _conexion = new DBconexion(context);
    }

    public DBmanager open() throws SQLException {
        _basededatos = _conexion.getWritableDatabase();
        return this;
    }

    public void insertUser(String usuario, String contrasena,String rol){
        ContentValues values = new ContentValues();//Esto es para almacenar valores y poderlos insertar en la BD
        values.put("usuario",usuario);
        values.put("contrasena",contrasena);
        values.put("rol",rol);
        _basededatos.insert(TABLA_USUARIOS,null,values);
    }

    public void insert(String tableName, ContentValues values) {
        _basededatos.insert(tableName, null, values);
    }

    public Cursor obtenerUsuarios(){
        String[] columnas = new String[]{USUARIO_ID,USUARIO,CONTRASENA};
        return _basededatos.query(TABLA_USUARIOS,columnas,null,null,null,null,null);
    }
    public Cursor getAll(String tableName, String[] columns) {
        return _basededatos.query(tableName, columns, null, null, null, null, null);
    }

    public Cursor obtenerUsuario(String usuario, String contrasena){
        String[] columnas = new String[]{USUARIO_ID,USUARIO,CONTRASENA,ROL};
        String seleccion = USUARIO + " = ? AND " + CONTRASENA + " =?";
        String[] argumentos = new String[]{usuario,contrasena};

        return _basededatos.query(TABLA_USUARIOS,columnas,seleccion,argumentos,null,null,null);
    }
    public Cursor get(String tableName, String[] columns, String selection, String[] selectionArgs) {
        return _basededatos.query(tableName, columns, selection, selectionArgs, null, null, null);
    }

    public void deleteAllDataFromTable(String tableName) {
        _basededatos.delete(tableName,null,null);
    }

    public void agregarColumna(String nombreTabla, String nombreColumna, String tipoDato){
        String sentenciaAlter = "ALTER TABLE " + nombreTabla + " ADD COLUMN " + nombreColumna + " " + tipoDato;
        _basededatos.execSQL(sentenciaAlter);
    }


    public Cursor obtenerCatalago() {
        String query = "SELECT secciones.nombre AS nombre_seccion, elementos.nombre AS nombre_elemento, item_seccion._id AS id_item_seccion " +
                "FROM secciones " +
                "JOIN item_seccion ON secciones._id = item_seccion.seccion_id " +
                "JOIN elementos ON item_seccion.item_id = elementos._id";

        return _basededatos.rawQuery(query, null);
    }

    public Cursor obtenerElementos(){

        String query = "SELECT elementos.nombre AS nombre, elementos._id AS id FROM elementos";
        return _basededatos.rawQuery(query, null);
    }

    public Cursor obtenerRegistros(int UsuarioId, int itemseleccionado) {
        String query = "SELECT formulario_registro._id AS fregistroid, formulario_registro.radiacion_recibida as radiacion, formulario_registro.consumo as consumo, formulario_registro.mes as mes, formulario_registro.dias_mant AS dias_mant ,formulario_registro.toneladasco2 AS toneladasco2,secciones.nombre AS nombreSeccion, elementos.nombre AS nombreElemento " +
                "FROM formulario_registro " +
                "JOIN item_seccion ON formulario_registro.catalogo_id = item_seccion._id " +
                "JOIN secciones ON item_seccion.seccion_id = secciones._id " +
                "JOIN elementos ON item_seccion.item_id = ? " +
                "WHERE formulario_registro.usuario_id = ? " +
                "ORDER BY CASE formulario_registro.mes " +
                "   WHEN 'Enero' THEN 1 " +
                "   WHEN 'Febrero' THEN 2 " +
                "   WHEN 'Marzo' THEN 3 " +
                "   WHEN 'Abril' THEN 4 " +
                "   WHEN 'Mayo' THEN 5 " +
                "   WHEN 'Junio' THEN 6 " +
                "   WHEN 'Julio' THEN 7 " +
                "   WHEN 'Agosto' THEN 8 " +
                "   WHEN 'Septiembre' THEN 9 " +
                "   WHEN 'Octubre' THEN 10 " +
                "   WHEN 'Noviembre' THEN 11 " +
                "   WHEN 'Diciembre' THEN 12 " +
                "END DESC LIMIT 2";

        String[] selectionArgs = {String.valueOf(itemseleccionado),String.valueOf(UsuarioId)};
        return _basededatos.rawQuery(query, selectionArgs);
    }
    public Cursor obtenerTodosRegistros(int UsuarioId) {
        String query = "SELECT formulario_registro._id AS fregistroid, formulario_registro.radiacion_recibida as radiacion, formulario_registro.consumo as consumo, formulario_registro.mes as mes, formulario_registro.dias_mant AS dias_mant ,formulario_registro.toneladasco2 AS toneladasco2,secciones.nombre AS nombreSeccion, elementos.nombre AS nombreElemento " +
                "FROM formulario_registro " +
                "JOIN item_seccion ON formulario_registro.catalogo_id = item_seccion._id " +
                "JOIN secciones ON item_seccion.seccion_id = secciones._id " +
                "JOIN elementos ON item_seccion.item_id = elementos._id " +
                "WHERE formulario_registro.usuario_id = ? " +
                "ORDER BY CASE formulario_registro.mes " +
                "   WHEN 'Enero' THEN 1 " +
                "   WHEN 'Febrero' THEN 2 " +
                "   WHEN 'Marzo' THEN 3 " +
                "   WHEN 'Abril' THEN 4 " +
                "   WHEN 'Mayo' THEN 5 " +
                "   WHEN 'Junio' THEN 6 " +
                "   WHEN 'Julio' THEN 7 " +
                "   WHEN 'Agosto' THEN 8 " +
                "   WHEN 'Septiembre' THEN 9 " +
                "   WHEN 'Octubre' THEN 10 " +
                "   WHEN 'Noviembre' THEN 11 " +
                "   WHEN 'Diciembre' THEN 12 " +
                "END DESC LIMIT 2";

        String[] selectionArgs = {String.valueOf(UsuarioId)};
        return _basededatos.rawQuery(query, selectionArgs);
    }


    public Cursor ObtenerItemsCatalogo(int elementoId){
        String query = "SELECT t.nombre AS nombre_servicio, s.nombre AS nombre_solicitud " +
                "FROM elemento_tpts AS e " +
                "JOIN tiposdeservicio AS t ON e.tiposervicio_id = t._id " +
                "JOIN tiposdesolicitud AS s ON e.tiposolicitud_id = s._id ";

        return _basededatos.rawQuery(query, null);

    }

    public Cursor obtenerComentarios(int formregistroid, int usuariocreid){
        String query = "SELECT us.usuario AS nombre_usuario, rm.mensaje AS mensaje, rm.fecha_creacion AS fecha_creacion " +
                "FROM registros AS rm " +
                "JOIN formulario_registro AS fm ON rm.fregistro_id = fm._id " +
                "JOIN usuarios AS us ON rm.nombre_id = us._id " +
                "WHERE rm.fregistro_id = ? AND rm.nombre_id = ?";

        String[] selectionArgs = {String.valueOf(formregistroid), String.valueOf(usuariocreid)};

        return _basededatos.rawQuery(query, selectionArgs);
    }

    public Cursor obtenerUsuariosResolutores() {
        String query = "SELECT us.usuario AS usuario, us._id AS usuario_id " +
                "FROM usuarios AS us " +
                "WHERE rol = 1";
        return _basededatos.rawQuery(query, null);
    }

    public void actualizarRegistro(int id, String nuevoValor, String columna, String fecha,String nombre_tabla) {
        ContentValues valores = new ContentValues();
        valores.put(columna, nuevoValor);

        // Obtenemos la fecha actual en formato de texto
        String fechaActual = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        valores.put(fecha, fechaActual);

        //String whereClause = FormularioRegistro.COLUMN_ID+ " = ?";
        String[] whereArgs = {String.valueOf(id)};

        //_basededatos.update(nombre_tabla, valores, whereClause, whereArgs);
    }

    public void close() {
        _conexion.close();
    }
}