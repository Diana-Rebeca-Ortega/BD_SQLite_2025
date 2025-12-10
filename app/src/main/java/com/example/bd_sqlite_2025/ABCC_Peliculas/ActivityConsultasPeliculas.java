package com.example.bd_sqlite_2025.ABCC_Peliculas; // Asume que esta Activity está aquí, si no, ajusta el paquete

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bd_sqlite_2025.R;

import Entities.Pelicula;
import db.videosBDD;

public class ActivityConsultasPeliculas extends AppCompatActivity implements PeliculaClickListener {

    private PeliculaAdapter adapter;
    private RecyclerView recyclerView;
    private EditText etBuscarTitulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas_peliculas);
        etBuscarTitulo = findViewById(R.id.et_buscar_titulo);
        configurarRecyclerView();
        observarDatosPeliculas();
        configurarFiltro();
    }
    private void configurarFiltro() {
        etBuscarTitulo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Llamamos al método de filtrado cada vez que el texto cambia
                if (adapter != null) {
                    adapter.filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void configurarRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view_consulta_peliculas);

        // 1. Inicializar el Adaptador, pasándole 'this' como el Context y 'this' como el ClickListener
        adapter = new PeliculaAdapter(this, this);

        // 2. Asignar el Layout Manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 3. Asignar el Adaptador
        recyclerView.setAdapter(adapter);
    }

    private void observarDatosPeliculas() {
        videosBDD db = videosBDD.getAppDatabase(getApplicationContext());

        // Observamos el LiveData para obtener la lista
        db.peliculaDao().getAllPeliculas().observe(this, peliculas -> {
            if (peliculas != null) {
                // Actualizar la lista en el adaptador
                adapter.setListaPeliculas(peliculas);
            }
        });
    }

    // 4. Implementar el método del ClickListener (Modificación)
    @Override
    public void onPeliculaClick(Pelicula pelicula) {
        lanzarDialogoModificar(pelicula);
    }

    private void lanzarDialogoModificar(Pelicula pelicula) {
        ModificarPeliculaDialog dialog = new ModificarPeliculaDialog();

        Bundle bundle = new Bundle();
        bundle.putInt("PELICULA_ID", pelicula.getId_pelicula());
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "ModificarPelicula");
    }
}