package com.example.solarsports.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.solarsports.FormularioActivity;
import com.example.solarsports.R;
import com.example.solarsports.models.Elemento;

import java.util.List;

public class ElementoAdapter extends RecyclerView.Adapter<ElementoAdapter.ElementoViewHolder> {

    private List<Elemento> elementos;
    private int idcatalogo;
    private Context context;
    private int idUsuario;
    private String rol;

    public ElementoAdapter(List<Elemento> elementos, int idcatalogo , Context context, int idUsuario, String rol) {
        this.elementos = elementos;
        this.context = context;
        this.idcatalogo = idcatalogo;
        this.idUsuario = idUsuario;
        this.rol = rol;
    }

    public class ElementoViewHolder extends RecyclerView.ViewHolder {
        // Añade aquí los elementos individuales del catálogo (imágenes, texto, etc.)
        private TextView nombreElemento;

        public ElementoViewHolder(View itemView) {
            super(itemView);
            // Inicializa aquí los elementos individuales del catálogo
            nombreElemento = itemView.findViewById(R.id.nombre_elemento);
        }
    }

    @Override
    public ElementoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_catalogo, parent, false);

        // Calcula el ancho del elemento del RecyclerView. Esto para que en el Recylcerview horizontal se divida en dos elementos a nivel vista.
        int screenWidth = parent.getResources().getDisplayMetrics().widthPixels;
        int itemWidth = screenWidth / 2; // Divide la pantalla en 2 partes iguales
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = itemWidth;
        view.setLayoutParams(layoutParams);
        return new ElementoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ElementoViewHolder holder, int position) {
        Elemento elemento = elementos.get(position);
        holder.nombreElemento.setText(elemento.getNombre());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemId = elemento.getId();
                String nombreElemento = elemento.getNombre();
                Intent intent = new Intent(context, FormularioActivity.class);
                intent.putExtra("itemId", idcatalogo); // Pasar el ID o cualquier otro dato necesario
                intent.putExtra("NombreElemento", nombreElemento); // Pasar el Nombre o cualquier otro dato necesario
                intent.putExtra("idUsuario", idUsuario); // Agregar el idUsuario al Intent
                intent.putExtra("rol",rol);
                context.startActivity(intent);
            }
        });

        // Configura aquí los elementos individuales del catálogo
    }

    @Override
    public int getItemCount() {
        return elementos.size();
    }
}
