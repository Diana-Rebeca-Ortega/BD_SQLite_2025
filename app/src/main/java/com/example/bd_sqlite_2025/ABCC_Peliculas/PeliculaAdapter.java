package com.example.bd_sqlite_2025.ABCC_Peliculas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button; // Necesario para el botón EDITAR
import android.widget.TextView;
import com.example.bd_sqlite_2025.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import Entities.Pelicula;

public class PeliculaAdapter extends RecyclerView.Adapter<PeliculaAdapter.PeliculaViewHolder> {

    private final LayoutInflater mInflater;
    private final PeliculaClickListener clickListener;

    // VARIABLES CORREGIDAS: Usamos mPeliculas para la lista mostrada y listaCompleta para la original.
    private List<Pelicula> mPeliculas = Collections.emptyList();
    private List<Pelicula> listaCompleta = Collections.emptyList();

    // Constructor
    public PeliculaAdapter(Context context, PeliculaClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public PeliculaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Asegúrate de que este layout (item_pelicula.xml) contenga el botón EDITAR
        View itemView = mInflater.inflate(R.layout.item_pelicula, parent, false);
        return new PeliculaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PeliculaViewHolder holder, int position) {
        final Pelicula current = mPeliculas.get(position); // Usar final si se usa en el Listener

        // 1. Enlazar datos (Esto está correcto)
        holder.tvId.setText(String.valueOf(current.getId_pelicula()));
        holder.tvTitulo.setText(current.getTitulo());
        holder.tvCategoria.setText(current.getCategoria());
        holder.tvAlquiler.setText(String.format("$%.2f", current.getAlquilerDiario()));
        holder.tvCosteVenta.setText(String.format("$%.2f", current.getCosteVenta()));
        holder.tvStockTotal.setText(String.valueOf(current.getStockTotal()));

        // 2. ¡IMPLEMENTACIÓN DEL LISTENER PARA MODIFICAR!
        // Necesitas el ID del botón EDITAR en tu ViewHolder o buscarlo aquí
        Button btnEditar = holder.itemView.findViewById(R.id.btn_editar_pelicula);

        btnEditar.setOnClickListener(v -> {
            if (clickListener != null) {
                // Al hacer clic en el botón EDITAR, se llama al método de la Activity.
                clickListener.onPeliculaClick(current);
            }
        });

        // Si antes tenías un Listener en todo el itemView, lo debes ELIMINAR/COMENTAR:
        // holder.itemView.setOnClickListener(v -> { ... });
    }

    @Override
    public int getItemCount() {
        return mPeliculas.size();
    }

    // --- MÉTODOS DE FILTRADO Y ACTUALIZACIÓN ---

    // 1. Método ÚNICO para actualizar y guardar la lista completa
    public void setListaPeliculas(List<Pelicula> peliculas) {
        this.mPeliculas = peliculas;
        // CRÍTICO: Usar una nueva instancia (copia) para evitar modificar la lista original
        this.listaCompleta = new ArrayList<>(peliculas);
        notifyDataSetChanged();
    }

    // 2. Método de Filtrado (La lógica que añadiste está perfecta)
    public void filter(String texto) {
        if (listaCompleta == null || listaCompleta.isEmpty()) return;

        mPeliculas.clear();

        if (texto.isEmpty()) {
            mPeliculas.addAll(listaCompleta);
        } else {
            texto = texto.toLowerCase(Locale.getDefault());
            for (Pelicula pelicula : listaCompleta) {
                if (pelicula.getTitulo().toLowerCase(Locale.getDefault()).contains(texto) ||
                        (pelicula.getDirector() != null && pelicula.getDirector().toLowerCase(Locale.getDefault()).contains(texto))) {
                    mPeliculas.add(pelicula);
                }
            }
        }
        notifyDataSetChanged();
    }

    // --- VIEWHOLDER ---
    public static class PeliculaViewHolder extends RecyclerView.ViewHolder {
        // ... (Declaraciones de TextViews) ...
        private final TextView tvId;
        private final TextView tvTitulo;
        private final TextView tvCategoria;
        private final TextView tvAlquiler;
        private final TextView tvCosteVenta;
        private final TextView tvStockTotal;
        // private final Button btnEditar; // Puedes declararlo aquí también si lo prefieres

        private PeliculaViewHolder(View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id_pelicula);
            // ... (Inicialización del resto de TextViews) ...
            tvTitulo = itemView.findViewById(R.id.tv_titulo);
            tvCategoria = itemView.findViewById(R.id.tv_categoria);
            tvAlquiler = itemView.findViewById(R.id.tv_alquiler);
            tvCosteVenta = itemView.findViewById(R.id.tv_coste_venta);
            tvStockTotal = itemView.findViewById(R.id.tv_stock_total);
            // btnEditar = itemView.findViewById(R.id.btn_editar_pelicula); // Si lo declaras arriba, inicialízalo aquí
        }
    }
}