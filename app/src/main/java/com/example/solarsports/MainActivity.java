package com.example.solarsports;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.solarsports.DB.DBmanager;
import com.example.solarsports.models.FirebaseAuthManager;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUsuario;
    private EditText editTextContrasena;
    private Button btnIniciarSesion;
    private LoadingPopup loadingPopup;
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private int idUsuario;
    private String rol;

    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
    private boolean showOneTapUI = true;
    private SignInClient oneTapClient;
    public FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleSignInClient.signOut();

        // [END config_signin]

        // [START initialize_auth]
        // Initialize Firebase Auth
        // Inicializar la autenticación de Firebase utilizando FirebaseAuthManager
        mAuth = FirebaseAuthManager.getInstance().getFirebaseAuth();
        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mAuth.getCurrentUser() == null) {
                    // El usuario ha cerrado la sesión
                    Log.d("AUTH", "El usuario ha cerrado la sesión");
                } else {
                    // El usuario ha iniciado sesión
                    Log.d("AUTH", "El usuario ha iniciado sesión");
                }
            }
        };

// Añade el AuthStateListener
        mAuth.addAuthStateListener(authListener);


        // Obtén una referencia al botón por su ID
        Button btnClickMe = findViewById(R.id.btnIniciarSesion2);

        // Configura el OnClickListener para el botón
        btnClickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí se ejecuta el código cuando se hace clic en el botón
                // Puedes realizar cualquier acción que desees aquí
                // Por ejemplo, mostrar un mensaje en el log o mostrar un Toast
                signIn();
                Log.d("MainActivity", "Botón clickeado");
                Toast.makeText(MainActivity.this, "Botón clickeado", Toast.LENGTH_SHORT).show();
            }
        });


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
                            idUsuario = cursor.getInt(cursor.getColumnIndexOrThrow(DBmanager.USUARIO_ID));
                            rol = cursor.getString(cursor.getColumnIndexOrThrow(DBmanager.ROL));
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
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    private void updateUI(FirebaseUser user) {
    }
    public FirebaseAuth initializeFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Verificar si el usuario está autenticado
        FirebaseUser currentUser = mAuth.getCurrentUser();
            Log.d(TAG, "firebaholaaaaaa" + currentUser);
            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            if (requestCode == RC_SIGN_IN) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                    firebaseAuthWithGoogle(account.getIdToken());
                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    Log.w(TAG, "Google sign in failed", e);
                }
        }
    }
    // [START auth_with_google]
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success"+mAuth.getCurrentUser().getEmail());
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(MainActivity.this, PortafolioActivity.class);
                            intent.putExtra("idUsuario", idUsuario); // Pasar el valor de idUsuario a la otra actividad
                            intent.putExtra("rol",rol);
                            // Simulación de consulta exitosa// Cambia esto según tu lógica real
                            startActivity(intent);
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }
    protected void onResume() {
        super.onResume();

        // Verificar si el usuario está autenticado
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            mAuth.signOut();
        }else{
        }
    }
    // [END auth_with_google]
    // [START signin]
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]
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