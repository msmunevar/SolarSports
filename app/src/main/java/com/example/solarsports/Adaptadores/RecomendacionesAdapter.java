package com.example.solarsports.Adaptadores;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.solarsports.R;
import com.example.solarsports.models.Elemento;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecomendacionesAdapter extends RecyclerView.Adapter<RecomendacionesAdapter.SeccionViewHolder> {

    private List<String> recomendaciones;
    private Context context;
    private int idUsuario;
    private String rol;
    private int tamano_secciones;

    public RecomendacionesAdapter(List<String> recomendaciones, Context context, int idUsuario, String rol) {
        this.recomendaciones = recomendaciones;
        this.context = context;
        this.idUsuario = idUsuario;
        this.rol = rol;
    }

    public class SeccionViewHolder extends RecyclerView.ViewHolder {
        private TextView textSeccion;
        private RecyclerView recyclerCatalogo;

        public SeccionViewHolder(View itemView) {
            super(itemView);
            textSeccion = itemView.findViewById(R.id.text_seccion);
            recyclerCatalogo = itemView.findViewById(R.id.recycler_recomendaciones);
        }
    }

    @Override
    public SeccionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recomendacion, parent, false);
        return new SeccionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeccionViewHolder holder, int position) {
        String nombreRecomendacion = recomendaciones.get(position);
        holder.textSeccion.setText(nombreRecomendacion);
        Log.d("CatalogoAdapter", "onBindViewHolder: Sección " + nombreRecomendacion+ " enlazada");
    }

    @Override
    public int getItemCount() {
        return recomendaciones != null ? recomendaciones.size(): 0;
    }
}
