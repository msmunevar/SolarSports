package com.example.solarsports;

// Importaciones necesarias

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.solarsports.Adaptadores.RegistrosAdapter;
import com.example.solarsports.DB.DBmanager;
//import com.example.solarsports.ViewModels.ViewModelEstDet;
import com.example.solarsports.models.Elemento;
import com.example.solarsports.models.FormularioRegistro;
import com.example.solarsports.DB.DBmanager;
import com.example.solarsports.models.FormularioRegistro;
import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class fragment_registros extends Fragment{

    private static final String ARG_USUARIO_ID = "usuarioId";
    private static final String ARG_ROL = "rol";
    private int usuarioId;
    private String rol;
    private List<FormularioRegistro> listaRegistros;
    private SharedPreferences sharedPreferences;

    private static final int CODIGO_DETALLE_ACTIVITY = 1;
    private ProgressBar progressBar;
    private Cursor cursor;
    private Cursor cursor2;
    private TextInputEditText editText2;
    private View rootView;
    private DBmanager dbmanager;
    private double consumoDifference;
    private ObjectAnimator progressAnimator;

    /*@Override
    public void onItemClicked(int posicion, int itemId,int idUsuario, String rol, String estado) {
        // Aquí puedes obtener el elemento seleccionado del RecyclerView según la posición
        // y realizar las actualizaciones necesarias.

        // Guardar datos en SharedPreferences
        /*SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("estado", estado);
        editor.putInt("regId", itemId);
        editor.putInt("idUsuario", idUsuario);
        editor.putInt("posicion", posicion);
        editor.putString("rol", rol);
        editor.apply();


    }*/


    public fragment_registros() {
        // Required empty public constructor
    }

    public static fragment_registros newInstance(int usuarioId, String rol) {
        fragment_registros fragment = new fragment_registros();
        Bundle args = new Bundle();
        args.putInt(ARG_USUARIO_ID, usuarioId);
        args.putString(ARG_ROL, rol);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireActivity());
        if (getArguments() != null) {
            usuarioId = getArguments().getInt(ARG_USUARIO_ID);
            rol = getArguments().getString(ARG_ROL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_registros, container, false);

        dbmanager = new DBmanager(requireContext());
        dbmanager.open();

        cursor2 = dbmanager.obtenerElementos();

        ArrayList<Elemento> valoresUnicos = new ArrayList<>();
        if (cursor2.moveToFirst()) {
            do {
                // Suponiendo que "nombre" es el nombre de la columna que deseas obtener
                String nombre = cursor2.getString(cursor2.getColumnIndexOrThrow("nombre"));
                int id = cursor2.getInt(cursor2.getColumnIndexOrThrow("id"));
                // Crear un objeto Elemento y agregarlo a la lista
                Elemento elemento = new Elemento(nombre);
                elemento.setId(id);
                // Agregar el valor a la lista
                valoresUnicos.add(elemento);
            } while (cursor2.moveToNext());
        }
        cursor2.close();

        // Convertir la lista a un array de String
        // Convertir la lista a un array de Elemento
        Elemento[] opciones = valoresUnicos.toArray(new Elemento[valoresUnicos.size()]);

// Crear un ArrayAdapter con el contexto del fragmento, un diseño de fila predefinido y el array de opciones
        ArrayAdapter<Elemento> adapter1 = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, opciones);
        editText2 = rootView.findViewById(R.id.editText2);
        configureEditText(editText2, adapter1);

        /*
        // Crear una instancia del adaptador del RecyclerView principal
        this.registrosAdapter = new RegistrosAdapter(listaRegistros, getContext(), usuarioId, rol);

        // Buscar una vista dentro de la vista inflada del fragmento
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_registros);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 1, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(registrosAdapter);
        //se utiliza para establecer un listener (escuchador) personalizado en el adaptador. ES IMPORTANTE
        registrosAdapter.setOnItemClickListener(this);*/

        return rootView;
    }

    private void configureEditText(TextInputEditText editText, ArrayAdapter<Elemento> adapter) {
        editText.setInputType(InputType.TYPE_NULL);
        editText.setTypeface(null, Typeface.BOLD);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_layout, null);
        ListView bottomSheetListView = bottomSheetView.findViewById(R.id.bottom_sheet_listview);
        bottomSheetListView.setAdapter(adapter);

        bottomSheetDialog.setContentView(bottomSheetView);

        AdapterView.OnItemClickListener itemClickListener = (parent, view, position, id) -> {
            Elemento selectedElemento = adapter.getItem(position);
            if (selectedElemento != null) {
                String selectedOption = selectedElemento.getNombre();
                int selectedId = selectedElemento.getId();

                editText.setText(selectedOption);
                actualizarRegistros(selectedId);
                // Puedes hacer lo que necesites con el ID aquí
                Log.d("ItemSelectedID", ""+selectedId);
                bottomSheetDialog.dismiss();
            }
        };

        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                bottomSheetListView.setOnItemClickListener(itemClickListener);
                bottomSheetDialog.show();
            } else {
                Log.d("Validación", "Usuario válido: ID: ");
            }
        });

        editText.setOnClickListener(v -> {
            Log.d("Validación", "Usuario válido: ID2: ");
            bottomSheetListView.setOnItemClickListener(itemClickListener);
            bottomSheetDialog.show();
        });

        bottomSheetDialog.setOnDismissListener(dialog -> {
            editText.clearFocus();
        });
    }

    private void actualizarRegistros(int elementoSeleccionado) {
        Log.d("PortafolioActivity", "hola" + elementoSeleccionado);
        Cursor cursor = dbmanager.obtenerRegistros(usuarioId,elementoSeleccionado);
        listaRegistros = new ArrayList<>();

        if (cursor.moveToFirst()) {
            double sumaRadiacion = 0.0;
            double sumaConsumo = 0.0;
            int numRegistros = 0;
            double previousConsumo = 0.0;
            double valorConsumo = 0.0;
            int dias_mant = 0;
            double toneladaco2 = 0.0;
            String nombreSeccion;
            String nombreElemento;
            String radiacion;
            String consumo;
            int formregistroid;
            do {
                nombreSeccion = cursor.getString(cursor.getColumnIndexOrThrow("nombreSeccion"));
                nombreElemento = cursor.getString(cursor.getColumnIndexOrThrow("nombreElemento"));
                radiacion = cursor.getString(cursor.getColumnIndexOrThrow("radiacion"));
                consumo = cursor.getString(cursor.getColumnIndexOrThrow("consumo"));
                dias_mant= Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("dias_mant")));
                toneladaco2 = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("toneladasco2")));
                String mes = cursor.getString(cursor.getColumnIndexOrThrow("mes"));
                formregistroid = cursor.getInt(cursor.getColumnIndexOrThrow("fregistroid"));

                // Convertir la radiación a un valor numérico (por ejemplo, double) si es necesario
                double valorRadiacion = Double.parseDouble(radiacion);
                valorConsumo = Double.parseDouble(consumo);

                FormularioRegistro registro = new FormularioRegistro(nombreSeccion, nombreElemento, formregistroid);
                listaRegistros.add(registro);
                // Acumular los valores de radiación
                sumaRadiacion += valorRadiacion;
                sumaConsumo += valorConsumo;
                consumoDifference = (valorConsumo - previousConsumo);
                previousConsumo = valorConsumo;

                numRegistros++;

            } while (cursor.moveToNext());
            if (numRegistros > 0) {
                consumoDifference =consumoDifference/previousConsumo;
                double promedioRadiacion = sumaRadiacion / numRegistros;
                double promedioConsumo = sumaConsumo / numRegistros;
                double porcentajeRaCon = promedioConsumo / promedioRadiacion;
                String porcentajeFormateado = String.format("%.2f%%", porcentajeRaCon * 100);

                /*
                DatosCompartidos nuevaInstancia = DatosCompartidos.getInstance();
                nuevaInstancia.setDatos(
                        consumoDifference, promedioRadiacion, promedioConsumo, porcentajeRaCon,porcentajeFormateado,dias_mant,toneladaco2);
                */
                //

                Log.d("PortafolioActivity", "Difference: " + consumoDifference);
                if (consumoDifference > 0) {
                    // Diferencia positiva, establecer flecha hacia arriba y color verde
                    ((TextView) rootView.findViewById(R.id.difind)).setText(String.format("%.2f%% \u2191", consumoDifference * 100));
                    ((TextView) rootView.findViewById(R.id.difind)).setTextColor(Color.GREEN);
                } else if (consumoDifference < 0) {
                    // Diferencia negativa, establecer flecha hacia abajo y color rojo
                    ((TextView) rootView.findViewById(R.id.difind)).setText(String.format("%.2f%% \u2193", consumoDifference * 100));
                    ((TextView) rootView.findViewById(R.id.difind)).setTextColor(Color.RED);
                }

                if (dias_mant <5) {
                    // Diferencia positiva, establecer símbolo "A tiempo" y color verde
                    ((TextView) rootView.findViewById(R.id.disminucionmant)).setText(String.format("\u26A0 %d", dias_mant));
                } else if (dias_mant > 5) {
                    // Diferencia negativa, establecer símbolo "Alerta" y color rojo
                    ((TextView) rootView.findViewById(R.id.disminucionmant)).setText(String.format("\u2714 %d", dias_mant));
                    Log.d("PortafolioActivity", "dias" + dias_mant);
                }

                ((TextView) rootView.findViewById(R.id.textview_progress_status_id)).setText(porcentajeFormateado);
                progressBar = rootView.findViewById(R.id.progress_circular_id);
                progressBar.setProgress((int) Math.round(porcentajeRaCon * 100));
                ((TextView) rootView.findViewById(R.id.ahorroind)).setText(String.format("%,.2f", promedioConsumo));
                ((TextView) rootView.findViewById(R.id.huellaco2)).setText(String.format("%,.2f", toneladaco2));
                //((TextView) rootView.findViewById(R.id.difind)).setText(String.format("%.2f%%", consumoDifference * 100));

                // Crear un ObjectAnimator para animar la propiedad "progress" de la ProgressBar
                progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, (int) Math.round(porcentajeRaCon * 100));
                progressAnimator.setDuration(2000); // Duración de la animación en milisegundos

                // Iniciar la animación
                progressAnimator.start();

                Log.d("PortafolioActivity", "Promedio de radiación: " + porcentajeRaCon);
            } else {
                Log.d("PortafolioActivity", "No hay registros para calcular el promedio de radiación.");
            }
        }else{
            if (progressAnimator != null && progressAnimator.isRunning()) {
                progressAnimator.end(); //Para finalizar si ya está corriendo una.
            }
            ((TextView) rootView.findViewById(R.id.textview_progress_status_id)).setText("0.00%");
            progressBar = rootView.findViewById(R.id.progress_circular_id);
            ((TextView) rootView.findViewById(R.id.difind)).setText(String.format("%.2f%%", consumoDifference * 100));
            ((TextView) rootView.findViewById(R.id.difind)).setTextColor(Color.BLACK);
            ((TextView) rootView.findViewById(R.id.difind)).setText("0%");
            ((TextView) rootView.findViewById(R.id.huellaco2)).setText("0");
            ((TextView) rootView.findViewById(R.id.disminucionmant)).setText(String.format("0"));
            ((TextView) rootView.findViewById(R.id.ahorroind)).setText(String.format("0"));
            progressBar.setProgress(0);
        }
    }

    /*private ActivityResultLauncher<Intent> detalleActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // Obtener los datos modificados del Intent
                    Intent data = result.getData();
                    if (data != null) {
                        int posicionModificada = data.getIntExtra("posicionModificada", -1);
                        String estadoModificado = data.getStringExtra("estadoModificado");
                        Log.d("PortafolioActivity", "Sección agregada: " + estadoModificado);

                        // Actualizar el elemento seleccionado en la lista de registros o datos
                        if (posicionModificada != -1) {
                            FormularioRegistro registroModificado = listaRegistros.get(posicionModificada);
                            Log.d("PortafolioActivity", "Sección agregada: " + posicionModificada);
                            registroModificado.setEstado(estadoModificado);

                            // Notificar al adaptador que los datos han cambiado
                            registrosAdapter.notifyItemChanged(posicionModificada);
                        }
                    }
                }
            }
    );*/
}