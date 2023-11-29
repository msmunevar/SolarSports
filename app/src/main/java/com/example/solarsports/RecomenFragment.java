package com.example.solarsports;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.solarsports.Adaptadores.RecomendacionesAdapter;
import com.example.solarsports.DB.DBmanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecomenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecomenFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USUARIO_ID = "usuarioId";
    private static final String ARG_ROL = "rol";
    private int usuarioId;
    private String rol;
    private SharedPreferences sharedPreferences;
    private int opcionSeleccionada = 1;

    private double promedioRadiacion;
    private double promedioConsumo;
    private int dias_mant = 0;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RecomenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RecomenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecomenFragment newInstance(int usuarioId, String rol) {
        RecomenFragment fragment = new RecomenFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recomen, container, false);
        calcularRecomendaciones(usuarioId);

        ArrayList<String> recomendacionesArray = new ArrayList<>();

        view.findViewById(R.id.opcion1).setBackgroundResource(R.color.grisclaro2);
        ((TextView) view.findViewById(R.id.opcion1)).setTextColor(getResources().getColor(android.R.color.white));

        if ((promedioRadiacion - promedioConsumo) <= 0) {
            recomendacionesArray.clear();
            recomendacionesArray.addAll(Arrays.asList(
                    "Cambie a bombillas LED para reducir el consumo de energía. 💡",
                    "Apague dispositivos electrónicos cuando no estén en uso. 📴",
                    "Elija electrodomésticos con etiqueta de eficiencia energética. 🌿",
                    "Ajuste la temperatura del termostato para ahorrar energía. 🌡️",
                    "Reduzca la temperatura del calentador de agua y considere opciones como calentadores solares. ☀️"
            ));
        } else if ((promedioRadiacion - promedioConsumo) > 0) {
            recomendacionesArray.clear();
            recomendacionesArray.addAll(Arrays.asList(
                    "Usa energía solar al programar electrodomésticos para horas de mayor producción. ⏰🌞",
                    "Instala baterías para usar exceso de energía en momentos de baja producción solar. 🔋🌙",
                    "Ajusta orientación y ángulo para captar más luz solar. ☀️🔄",
                    "Cambia a dispositivos eficientes para reducir consumo y maximizar energía solar. 🌿💡",
                    "Utiliza sistemas para ajustar consumo y aprovechar energía solar disponible. 📊🌐"
            ));
        } // Agrega esta llave que cierra el bloque else if
        System.out.println("hola" + recomendacionesArray);
        RecomendacionesAdapter recomendacionAdapter = new RecomendacionesAdapter(recomendacionesArray,requireActivity(),usuarioId,rol);
        // Configurar el RecyclerView principal
        RecyclerView recyclerView = view.findViewById(R.id.recycler_recomendaciones);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerView.setAdapter(recomendacionAdapter);

        // Asignar el clic del botón al método onOptionClick
        view.findViewById(R.id.opcion1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionClick(v, 1);
                if ((promedioRadiacion - promedioConsumo) <= 0) {
                    recomendacionesArray.clear();
                    recomendacionesArray.addAll(Arrays.asList(
                            "Cambie a bombillas LED para reducir el consumo de energía. 💡",
                            "Apague dispositivos electrónicos cuando no estén en uso. 📴",
                            "Elija electrodomésticos con etiqueta de eficiencia energética. 🌿",
                            "Ajuste la temperatura del termostato para ahorrar energía. 🌡️",
                            "Reduzca la temperatura del calentador de agua y considere opciones como calentadores solares. ☀️"
                    ));
                } else if ((promedioRadiacion - promedioConsumo) > 0) {
                    recomendacionesArray.clear();
                    recomendacionesArray.addAll(Arrays.asList(
                            "Usa energía solar al programar electrodomésticos para horas de mayor producción. ⏰🌞",
                            "Instala baterías para usar exceso de energía en momentos de baja producción solar. 🔋🌙",
                            "Ajusta orientación y ángulo para captar más luz solar. ☀️🔄",
                            "Cambia a dispositivos eficientes para reducir consumo y maximizar energía solar. 🌿💡",
                            "Utiliza sistemas para ajustar consumo y aprovechar energía solar disponible. 📊🌐"
                    ));
                } // Agrega esta llave que cierra el bloque else if
                System.out.println("hola" + recomendacionesArray);

                recomendacionAdapter.notifyDataSetChanged();
            }
        });

        view.findViewById(R.id.opcion2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionClick(v, 2);

                if (dias_mant <= 180) {
                    recomendacionesArray.clear();
                    recomendacionesArray.addAll(Arrays.asList(
                            "Elimina polvo y suciedad para mantener la eficiencia. 🧹",
                            "Busca daños o conexiones sueltas en los paneles. 🔍",
                            "Evita sombras de vegetación cercana. 🌳",
                            "Asegúrate de que el cableado esté en buen estado. 🔧",
                            "Lleva un registro para detectar cambios significativos. 📈"
                    ));
                } else if (dias_mant > 180) {
                    recomendacionesArray.clear();
                    recomendacionesArray.addAll(Arrays.asList(
                            "Establece contratos con profesionales para revisiones periódicas. 🛠️",
                            "Programa inspecciones detalladas realizadas por técnicos capacitados. 🔍",
                            "Consulta con expertos para ajustar y mejorar la eficiencia del sistema. 📈",
                            "Realiza inspecciones termográficas para detectar posibles problemas. 🌡️",
                            "Mantente al día con las tecnologías, considera la actualización de componentes si es necesario. ⚙️"
                    ));
                }
                recomendacionAdapter.notifyDataSetChanged(); //Es importante para notificar los cambios del array modificado.
            }
        });

        return view;
    }

    public void onOptionClick(View view,int opcion) {
        // Restablecer el color de fondo a colorNormal para todas las opciones
        getView().findViewById(R.id.opcion1).setBackgroundResource(R.color.white);
        getView().findViewById(R.id.opcion2).setBackgroundResource(R.color.white);

        // Obtener referencias a las opciones
        TextView opcion1 = getView().findViewById(R.id.opcion1);
        TextView opcion2 = getView().findViewById(R.id.opcion2);

        // Restablecer el color de texto a negro para todas las opciones
        opcion1.setTextColor(getResources().getColor(android.R.color.black));
        opcion2.setTextColor(getResources().getColor(android.R.color.black));

        // Establecer el color de fondo a colorSelected para la opción seleccionada
        view.setBackgroundResource(R.color.grisclaro2);
        // Establecer el color de texto a blanco para la opción seleccionada
        ((TextView) view).setTextColor(getResources().getColor(android.R.color.white));

        opcionSeleccionada = opcion;


        /*DatosCompartidos instanciaCompartida = DatosCompartidos.getInstance();

        // Obtener todos los valores establecidos en setDatos
        int diasMant = instanciaCompartida.getDiasMant();
        double toneladaCO2 = instanciaCompartida.getToneladaCO2();
        double consumoDifference = instanciaCompartida.getConsumoDifference();
        double promedioRadiacion = instanciaCompartida.getPromedioRadiacion();
        double promedioConsumo = instanciaCompartida.getPromedioConsumo();
        double porcentajeRaCon = instanciaCompartida.getPorcentajeRaCon();

        System.out.println("tonealadas"+toneladaCO2);*/
    }

    public void calcularRecomendaciones(int usuarioId) {
        DBmanager dbmanager = new DBmanager(requireContext());
        dbmanager.open();
        Cursor cursor = dbmanager.obtenerTodosRegistros(usuarioId);

        double sumaRadiacion = 0.0;
        double sumaConsumo = 0.0;
        int numRegistros = 0;
        double previousConsumo = 0.0;
        double valorConsumo = 0.0;
        double toneladaco2 = 0.0;
        String nombreSeccion;
        String nombreElemento;
        String radiacion;
        String consumo;
        int formregistroid;
        double consumoDifference;

        if (cursor.moveToFirst()) {
            do {
                nombreSeccion = cursor.getString(cursor.getColumnIndexOrThrow("nombreSeccion"));
                nombreElemento = cursor.getString(cursor.getColumnIndexOrThrow("nombreElemento"));
                radiacion = cursor.getString(cursor.getColumnIndexOrThrow("radiacion"));
                consumo = cursor.getString(cursor.getColumnIndexOrThrow("consumo"));
                dias_mant = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("dias_mant")));
                toneladaco2 = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("toneladasco2")));
                String mes = cursor.getString(cursor.getColumnIndexOrThrow("mes"));
                formregistroid = cursor.getInt(cursor.getColumnIndexOrThrow("fregistroid"));

                // Convertir la radiación a un valor numérico (por ejemplo, double) si es necesario
                double valorRadiacion = Double.parseDouble(radiacion);
                valorConsumo = Double.parseDouble(consumo);

                // Acumular los valores de radiación
                sumaRadiacion += valorRadiacion;
                sumaConsumo += valorConsumo;
                consumoDifference = (valorConsumo - previousConsumo);
                previousConsumo = valorConsumo;

                numRegistros++;

            } while (cursor.moveToNext());

            if (numRegistros > 0) {
                consumoDifference = consumoDifference / previousConsumo;
                promedioRadiacion = sumaRadiacion / numRegistros;
                promedioConsumo = sumaConsumo / numRegistros;
                double porcentajeRaCon = promedioConsumo / promedioRadiacion;
                String porcentajeFormateado = String.format("%.2f%%", porcentajeRaCon * 100);


            }
        } else {
            Log.d("PortafolioActivity", "No hay registros para calcular el promedio de radiación.");
        }
    }
}