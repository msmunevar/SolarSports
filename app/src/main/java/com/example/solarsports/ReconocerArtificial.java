package com.example.solarsports;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReconocerArtificial#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReconocerArtificial extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReconocerArtificial() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReconocerArtificial.
     */
    // TODO: Rename and change types and number of parameters
    public static ReconocerArtificial newInstance(String param1, String param2) {
        ReconocerArtificial fragment = new ReconocerArtificial();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_reconocer_artificial, container, false);

        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.fragment_containerArtificial);

        NavController navController = navHostFragment.getNavController();
        // Configura los destinos que deben tener una flecha de retroceso en la AppBar
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
                .build();

        Toolbar toolbar = rootView.findViewById(R.id.toolbarArtificial);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorencabezado));

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            // Verificar si el destino actual es el fragmento B
            if (destination.getId() == R.id.artificialcamera) {
                // Ocultar la Toolbar cuando estás en el fragmento B
                toolbar.setVisibility(View.GONE);
            }  else if (destination.getId() == R.id.tokencamera){
                // Mostrar la Toolbar en cualquier otro destino después de un retraso
                new Handler().postDelayed(() -> {
                    toolbar.setTitleTextColor(ContextCompat.getColor(requireContext(), android.R.color.white));
                    toolbar.setVisibility(View.VISIBLE);
                }, 300); // Cambia el valor 300 a la cantidad de milisegundos que desees de retraso
            }
        });

        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        return rootView;
    }
}