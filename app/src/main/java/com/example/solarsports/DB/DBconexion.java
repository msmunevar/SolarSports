package com.example.solarsports.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.solarsports.models.Catalogo;
import com.example.solarsports.models.Elemento;
import com.example.solarsports.models.FormularioRegistro;
import com.example.solarsports.models.Seccion;

public class DBconexion extends SQLiteOpenHelper {

    private static final String DB_NAME = "dbinvent";
    private static final int DB_VERSION = 25;

    public DBconexion(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(DBmanager.TABLA_USUARIOS_CREATE);
        sqLiteDatabase.execSQL(Elemento.CREATE_TABLE_QUERY);
        sqLiteDatabase.execSQL(Seccion.CREATE_TABLE_QUERY);
        sqLiteDatabase.execSQL(Catalogo.CREATE_TABLE_QUERY);
        sqLiteDatabase.execSQL(FormularioRegistro.CREATE_TABLE_QUERY);
        /*sqLiteDatabase.execSQL(TipoServicio.CREATE_TABLE_QUERY);
        sqLiteDatabase.execSQL(TipoSolicitud.CREATE_TABLE_QUERY);
        sqLiteDatabase.execSQL(CatalogoTipoServicioTipoSolicitud.CREATE_TABLE_QUERY);
        sqLiteDatabase.execSQL(RegistroMensaje.CREATE_TABLE_QUERY);
        sqLiteDatabase.execSQL(TransaccionesRegistro.CREATE_TABLE_QUERY);*/
        Log.d("DBconexion", "Tabla de usuarios creada con éxito");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldversion, int newversion) {

        if(sqLiteDatabase.getVersion()<DB_VERSION) {
            /*sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBmanager.TABLA_USUARIOS);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Elemento.TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Seccion.TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Catalogo.TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TipoServicio.TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TipoSolicitud.TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CatalogoTipoServicioTipoSolicitud.TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RegistroMensaje.TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TransaccionesRegistro.TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FormularioRegistro.TABLE_NAME);*/
        }
        onCreate(sqLiteDatabase);
    }
}