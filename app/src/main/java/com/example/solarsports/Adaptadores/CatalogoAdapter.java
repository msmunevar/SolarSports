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
import com.example.solarsports.models.Catalogo;
import com.example.solarsports.models.Elemento;
import com.example.solarsports.models.Seccion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CatalogoAdapter extends RecyclerView.Adapter<CatalogoAdapter.SeccionViewHolder> {

    private HashMap<String, List<Elemento>> catalogos;
    private Context context;
    private int idUsuario;
    private String rol;
    private int tamano_secciones;

    public CatalogoAdapter(HashMap<String, List<Elemento>> catalogos, Context context, int idUsuario, String rol) {
        this.catalogos = catalogos;
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
            recyclerCatalogo = itemView.findViewById(R.id.recycler_catalogo);
        }
    }

    @Override
    public SeccionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seccion, parent, false);
        return new SeccionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeccionViewHolder holder, int position) {
        List<String> secciones = new ArrayList<>(catalogos.keySet());
        String nombreSeccion = secciones.get(position);
        List<Elemento> elementos = catalogos.get(nombreSeccion);
        int idCatalogo = elementos.get(0).getIdItemSeccion();
        holder.textSeccion.setText(nombreSeccion);

        ElementoAdapter elementoAdapter = new ElementoAdapter(elementos,idCatalogo,context,idUsuario,rol);
        holder.recyclerCatalogo.setAdapter(elementoAdapter);

        // Configurar el diseño del RecyclerView horizontal
        // Establecer el número máximo de elementos por fila
        int elementosPorFila = 10; // Puedes ajustar este valor según tus necesidades

        // Obtener la cantidad total de elementos en el adaptador
        int totalElementos = elementoAdapter.getItemCount();

        // Calcular el número de filas que se mostrarán
        int numeroFilas = totalElementos > elementosPorFila ? 2 : 1;

        // Crear un GridLayoutManager con el número de filas calculado y orientación horizontal
        GridLayoutManager layoutManager = new GridLayoutManager(holder.recyclerCatalogo.getContext(), numeroFilas, GridLayoutManager.HORIZONTAL, false);

        holder.recyclerCatalogo.setLayoutManager(layoutManager);

        Log.d("CatalogoAdapter", "onBindViewHolder: Sección " + elementos + " enlazada");
    }

    @Override
    public int getItemCount() {
        return catalogos != null ? catalogos.size(): 0;
    }
}
