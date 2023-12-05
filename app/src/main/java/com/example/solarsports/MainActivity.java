package com.example.solarsports;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.solarsports.DB.DBconexion;
import com.example.solarsports.DB.DBmanager;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUsuario;
    private EditText editTextContrasena;
    private Button btnIniciarSesion;
    private LoadingPopup loadingPopup;
    private static final int REQUEST_CAMERA_PERMISSION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // El permiso ya está concedido, puedes usar la cámara.
        } else {
            // El permiso no está concedido, solicítalo al usuario.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }

        /*Intent intent = new Intent(this, DetalleActivity.class);
        startActivity(intent);*/

        DBmanager dbmanager = new DBmanager(this);
        dbmanager.open();

        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        // Guardar valor
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("globalVariable", "true");
        editor.apply();
        String valor = sharedPreferences.getString("globalVariable", "false");

        /*ContentValues values = new ContentValues();
        values.put("item_id",5);
        values.put("seccion_id",1);
        values.put("usuarioid_responsable",1);
        values.put("usuarioid_creador",1);
        values.put("descripcion","Ninguna");
        values.put("sla","2");
        dbmanager.insert("item_seccion",values);*/

        /*ContentValues values = new ContentValues();
        values.put("nombre","Instalaciones Deportivas Acuáticas");
        dbmanager.insert("secciones",values);*/

        /*ContentValues values = new ContentValues();
        values.put("nombre","Consulta Facturas");
        dbmanager.insert("elementos",values);*/
        /*ContentValues values = new ContentValues();
        values.put("nombre","Campos de Golf");
        dbmanager.insert("elementos",values);*/
        /*dbmanager.deleteAllDataFromTable("item_seccion");*/

        /*dbmanager.deleteAllDataFromTable("item_seccion");*/


        if(valor.equals("true")){

            dbmanager.insertUser("msmaR2","1234","1");
            ContentValues values = new ContentValues();
            values.put("item_id",1);
            values.put("seccion_id",1);
            values.put("usuarioid_responsable",1);
            values.put("usuarioid_creador",1);
            values.put("descripcion","Ninguna");
            values.put("sla","2");
            dbmanager.insert("item_seccion",values);

            ContentValues values2 = new ContentValues();
            values2.put("nombre","Instalaciones Deportivas Acuáticas");
            dbmanager.insert("secciones",values2);

            ContentValues values4 = new ContentValues();
            values4.put("nombre","Piscina olímpica");
            dbmanager.insert("elementos",values4);

            editor.putString("globalVariable", "false");
        }

        Cursor cursor1 = dbmanager.getAll("formulario_registro",null);
        if (cursor1 != null && cursor1.moveToFirst()) {
            do {
                String  idTipoServicio = cursor1.getString(cursor1.getColumnIndexOrThrow("radiacion_recibida"));
                System.out.println("Catalogo ID: " + idTipoServicio );
            } while (cursor1.moveToNext());
        }

        loadingPopup = new LoadingPopup(this);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextContrasena = findViewById(R.id.editTextContrasena);

        editTextUsuario.addTextChangedListener(textWatcher);
        editTextContrasena.addTextChangedListener(textWatcher);

        // Deshabilita el botón al inicio
        btnIniciarSesion.setEnabled(false);
        // Agrega TextWatchers a los campos de texto
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnIniciarSesion.setEnabled(false);
                loadingPopup.show();
                String usuarioingresado = editTextUsuario.getText().toString();
                String contrasenaIngresada = editTextContrasena.getText().toString();

                // Declarar la variable consultaExitosa fuera de la función anónima

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Cursor cursor = dbmanager.obtenerUsuario(usuarioingresado,contrasenaIngresada);
                        if(cursor != null && cursor.moveToFirst()){
                            int idUsuario = cursor.getInt(cursor.getColumnIndexOrThrow(DBmanager.USUARIO_ID));
                            String rol = cursor.getString(cursor.getColumnIndexOrThrow(DBmanager.ROL));
                            String nombreUsuario = cursor.getString(cursor.getColumnIndexOrThrow(DBmanager.USUARIO));

                            Log.d("Validación", "Usuario válido: ID: " + idUsuario + ", Nombre: " + nombreUsuario);

                            Intent intent = new Intent(MainActivity.this, PortafolioActivity.class);
                            intent.putExtra("idUsuario", idUsuario); // Pasar el valor de idUsuario a la otra actividad
                            intent.putExtra("rol",rol);
                            // Simulación de consulta exitosa// Cambia esto según tu lógica real
                            loadingPopup.dismiss();
                            startActivity(intent);
                            btnIniciarSesion.setEnabled(true);
                        }else {
                            // El usuario y/o la contraseña no coinciden en la base de datos
                            // Realiza las acciones necesarias aquí
                            Log.d("Validación", "Usuario no válido");
                            // Simulación de consulta exitosa
                            loadingPopup.dismiss();
                            btnIniciarSesion.setEnabled(true);
                            mostrarPopup();// Cambia esto según tu lógica real
                            // Mostrar un mensaje de error o realizar alguna otra acción según sea necesario
                        }

                        // Si la consulta fue exitosa, oculta la vista emergente de carg
                    }
                }, 5000); // Simular una espera de 5 segundos (5000 milisegundos)
            }
        });

        // Puedes cerrar la conexión si no la necesitas más
    }
    private void mostrarPopup() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_continco);

        // Configura el cuadro emergente como modal
        dialog.setCanceledOnTouchOutside(false); // Evita que se cierre al tocar fuera del cuadro emergente
        dialog.setCancelable(true); // Permite cerrar el cuadro emergente con la tecla "Atrás" del dispositivo

        Button btnCerrar = dialog.findViewById(R.id.btnCerrar);
        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss(); // Cierra el cuadro emergente al hacer clic en el botón de cerrar
            }
        });

        dialog.show();
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Cambia el fondo del botón según el estado
            // Verifica si ambos campos de texto tienen texto
            String usuario = editTextUsuario.getText().toString().trim();
            String contrasena = editTextContrasena.getText().toString().trim();

            if (!usuario.isEmpty() && !contrasena.isEmpty()) {
                btnIniciarSesion.setBackgroundColor(getResources().getColor(R.color.colorclaro));
                btnIniciarSesion.setTypeface(null, Typeface.BOLD);
            }else{
                btnIniciarSesion.setBackgroundColor(getResources().getColor(R.color.rojoapagado));
                btnIniciarSesion.setTypeface(null, Typeface.NORMAL);
            }

            // Habilita o deshabilita el botón en función de si ambos campos están llenos
            btnIniciarSesion.setEnabled(!usuario.isEmpty() && !contrasena.isEmpty());
        }
    };
}