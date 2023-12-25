package com.example.solarsports;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/*import com.example.mesaservicios.Adaptadores.CatalogoAdapter;
import com.example.mesaservicios.DB.DBmanager;
import com.example.mesaservicios.models.Catalogo;
import com.example.mesaservicios.models.Elemento;
import com.example.mesaservicios.models.Seccion;*/
import com.example.solarsports.Adaptadores.CatalogoAdapter;
import com.example.solarsports.DB.DBmanager;
import com.example.solarsports.models.Elemento;
import com.example.solarsports.models.FirebaseAuthManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PortafolioActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private AppBarConfiguration appBarConfiguration;
    private NavController navController;
    private int tamano_secciones;
    private MainActivity mainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portafolio);

        // Obtener el nombre de usuario completo
        String displayName = FirebaseAuthManager.getInstance().getFirebaseAuth().getCurrentUser().getDisplayName();

        // Dividir el nombre de usuario en palabras
        String[] words = displayName.split("\\s+");

        // Obtener la primera palabra (si hay al menos una palabra)
        String firstWord = words.length > 0 ? words[0] : "";

        // Referenciar al TextView en tu layout
        TextView textPersona = findViewById(R.id.textPersona);

        // Establecer la primera palabra en el TextView
        textPersona.setText("!Hola, "+firstWord);

        Log.d("HolaaaMauth", "ID de Item de Sección: " + FirebaseAuthManager.getInstance().getFirebaseAuth().getCurrentUser().getDisplayName());

        int idUsuario = getIntent().getIntExtra("idUsuario", 0);
        String rol = getIntent().getStringExtra("rol");

        DBmanager dbmanager = new DBmanager(this);
        dbmanager.open();

        Cursor cursor = dbmanager.obtenerCatalago();
        //List<Seccion> listaSecciones = new ArrayList<>();
        HashMap<String, List<Elemento>> listaCatalogos = new HashMap<>();
        //List<Catalogo> listaCatalogos = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                String nombreSeccion = cursor.getString(cursor.getColumnIndexOrThrow("nombre_seccion"));
                String nombreElemento = cursor.getString(cursor.getColumnIndexOrThrow("nombre_elemento"));
                int idItemSeccion = cursor.getInt(cursor.getColumnIndexOrThrow("id_item_seccion"));

                Log.d("PortafolioActivity", "ID de Item de Sección: " + idItemSeccion);

                //Catalogo catalogo = new Catalogo(idItemSeccion);
                //Seccion seccion = new Seccion(nombreSeccion);
                Elemento elemento = new Elemento(nombreElemento);

                if (listaCatalogos.containsKey(nombreSeccion)) {
                    // Si existe, agregar el elemento a la lista de elementos de esa sección
                    elemento.setIdItemSeccion(idItemSeccion); // Establecer el idItemSeccion en el elemento
                    listaCatalogos.get(nombreSeccion).add(elemento);
                } else {
                    // Si no existe, crear una nueva lista y agregar el elemento
                    List<Elemento> elementos = new ArrayList<>();
                    elemento.setIdItemSeccion(idItemSeccion);
                    elementos.add(elemento);
                    listaCatalogos.put(nombreSeccion, elementos);
                }

                /*seccion.getElementos().add(elemento);
                Log.d("PortafolioActivity", "Sección agregada: " + seccion.getNombre());
                catalogo.getSecciones().add(seccion);
                listaCatalogos.add(catalogo);
                tamano_secciones = catalogo.getSecciones().size();*/

            } while (cursor.moveToNext());
        }
        // Crear una instancia del adaptador del RecyclerView principal
        CatalogoAdapter catalogoAdapter = new CatalogoAdapter(listaCatalogos,PortafolioActivity.this,idUsuario,rol); //este contexto pasa primero por el catalogo adpater, y luego pasa al elemento adapter.

        // Configurar el RecyclerView principal
        RecyclerView recyclerView = findViewById(R.id.recycler_catalogo_principal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(catalogoAdapter);

        ConstraintLayout constraintLayoutAliado = findViewById(R.id.constraintencabezado);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        if (item.getItemId() == R.id.action_home) {
                            // Cargar el fragmento de inicio
                            constraintLayoutAliado.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);
                            Fragment existingFragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout);
                            if (existingFragment != null) {
                                // Eliminar el fragmento solo si ya está presente
                                getSupportFragmentManager().beginTransaction()
                                        .remove(existingFragment)
                                        .commit();
                            }
                            item.setChecked(true);
                            item.getIcon().setColorFilter(getResources().getColor(R.color.rojoapagado), PorterDuff.Mode.SRC_IN);
                            return true;
                        } else if (item.getItemId() == R.id.action_profile) {
                            // Cargar el fragmento de configuración
                            recyclerView.setVisibility(View.GONE);
                            constraintLayoutAliado.setVisibility(View.GONE);
                            Fragment fragment = fragment_registros.newInstance(idUsuario, rol);
                            loadFragment(fragment);
                            return true;
                        }else if(item.getItemId() == R.id.action_nps){
                            recyclerView.setVisibility(View.GONE);
                            constraintLayoutAliado.setVisibility(View.GONE);
                            Fragment fragment = RecomenFragment.newInstance(idUsuario,rol);
                            loadFragment(fragment);
                            return true;
                        }
                        return false;
                    }
                });
    }

    @Override
    public void onBackPressed() {
        // Crear un AlertDialog con el estilo personalizado
        AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.AlertDialogStyle)
                .setTitle("¡Espera!")
                .setMessage("¿Estás seguro de que quieres salir?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Si el usuario hace clic en "Sí", entonces permite que la actividad retroceda
                        Intent intent = new Intent(PortafolioActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();

        // Puedes ajustar el color de los botones también si lo deseas
        Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);

        positiveButton.setTextColor(getResources().getColor(R.color.colorclaro)); // Cambia colorAccent al color que desees
        negativeButton.setTextColor(getResources().getColor(R.color.colorclaro)); // Cambia colorAccent al color que desees
    }


    private void loadFragment(Fragment fragment) {
        // Verificar si el fragmento ya está presente para evitar la superposición
        Fragment existingFragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout);
        if (existingFragment == null || !existingFragment.getClass().getName().equals(fragment.getClass().getName())) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, fragment)
                    .commit();
        }
    }

}