package com.example.bd_sqlite_2025;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bd_sqlite_2025.ABCC_Peliculas.ActivityConsultasPeliculas;
import com.example.bd_sqlite_2025.ABCC_Peliculas.PeliculaClickListener;
import com.example.bd_sqlite_2025.ABCC_Peliculas.RegistrarPeliculaDialog;

import java.util.List;
import com.example.bd_sqlite_2025.ABCC_Peliculas.PeliculaAdapter;
import Entities.Pelicula;
import db.videosBDD;

public class CatalogoFragment extends Fragment implements PeliculaClickListener {

    private RecyclerView recyclerView;
    private PeliculaAdapter adapter;
    // La instancia de la base de datos no necesita ser de clase, pero la mantendremos si la usas mucho.
    // private videosBDD db;

    public CatalogoFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // 1. Inflar el layout fragment_catalogo.xml
        View view = inflater.inflate(R.layout.fragment_catalogo, container, false);

        // --- 2. Configurar el RecyclerView y el Adaptador ---
        configurarRecyclerView(view);

        // --- 3. Configurar el Botón AÑADIR ---
        Button btnAnadir = view.findViewById(R.id.btn_añadir_pelicula);
        btnAnadir.setOnClickListener(v -> {
            RegistrarPeliculaDialog dialog = new RegistrarPeliculaDialog();
            dialog.show(getParentFragmentManager(), "RegistrarPelicula");
        });
        Button btnConsultas = view.findViewById(R.id.btn_buscar_categoria);
        btnConsultas.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ActivityConsultasPeliculas.class);
            startActivity(intent);
        });
        return view;
    }

    // --- NUEVO MÉTODO CLAVE: Configuración del RecyclerView ---
    private void configurarRecyclerView(View view) {
        // 1. Obtener referencia del RecyclerView (ID: recycler_view_peliculas)
        recyclerView = view.findViewById(R.id.recycler_view_peliculas);

        // 2. Inicializar el Adaptador
        // Usamos requireContext() porque estamos dentro de un Fragment
        adapter = new PeliculaAdapter(requireContext(), this);

        // 3. ¡CORRECCIÓN CRÍTICA! Asignar el Layout Manager (Sin esto, la lista está vacía)
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // 4. Asignar el Adaptador al RecyclerView
        recyclerView.setAdapter(adapter);

        // 5. Comenzar la observación de los datos
        observarDatosPeliculas();
    }

    // --- NUEVO MÉTODO CLAVE: Observar el LiveData ---
    private void observarDatosPeliculas() {
        // 1. Obtener la instancia de la base de datos (videosBDD)
        videosBDD db = videosBDD.getAppDatabase(requireContext());

        // 2. Observar el LiveData que devuelve el DAO
        db.peliculaDao().getAllPeliculas().observe(getViewLifecycleOwner(), new Observer<List<Pelicula>>() {
            @Override
            public void onChanged(List<Pelicula> peliculas) {
                // Este método se llama inmediatamente al inicio y cada vez que hay una inserción o cambio.

                if (peliculas != null) {
                    // 3. Actualizar la lista en el adaptador
                    adapter.setListaPeliculas(peliculas);

                    // Si todo está correcto, aquí verás tus 2 registros
                    // (Puedes poner un Log.d aquí para confirmar que llegan los datos)
                }
            }
        });
    }

    // Asegúrate de que tu fragmento tenga el ciclo de vida correcto para la observación
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Al destruir la vista, liberamos la referencia del RecyclerView
        recyclerView = null;
    }

    @Override
    public void onPeliculaClick(Pelicula pelicula) {

    }
}