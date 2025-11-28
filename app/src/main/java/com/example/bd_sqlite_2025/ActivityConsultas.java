package com.example.bd_sqlite_2025;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import db.EscuelaBD;
import Entities.Alumno;

public class ActivityConsultas extends Activity {

    private RecyclerView recyclerView;
    private AlumnoAdapter adapter;
    EditText cajaFiltro;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);

        recyclerView = findViewById(R.id.recyclerview_alumnos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cajaFiltro = findViewById(R.id.caja_buscar);
        // 2. Implementa el TextWatcher
        cajaFiltro.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // Llama al método de filtro del adaptador
                adapter.filtrar(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No es necesario implementar nada aquí para el filtro
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // No es necesario implementar nada aquí para el filtro
            }
        });

        // Llamar a la función que carga los datos
        cargarAlumnos();
    }

    private void cargarAlumnos() {
        // 1. Ejecutar la consulta en un HILO DE FONDO (OBLIGATORIO)
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EscuelaBD bd = EscuelaBD.getAppDatabase(getApplicationContext());
                    // Obtener la lista de alumnos
                    final List<Alumno> listaAlumnos = bd.alumnoDAO().mostrarTodos();

                    // 2. Actualizar la UI en el HILO PRINCIPAL
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Crear el adaptador y asignarlo al RecyclerView
                            adapter = new AlumnoAdapter(listaAlumnos);
                            recyclerView.setAdapter(adapter);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    // Manejar errores
                }
            }
        }).start();
    }
}