package com.example.bd_sqlite_2025.ABCC_Peliculas;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bd_sqlite_2025.R;
import db.videosBDD;

public class ActivityBajasPeliculas extends AppCompatActivity {

    private EditText etIdPelicula;
    private Button btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Usar el layout que acabamos de crear
        setContentView(R.layout.activity_bajas_peliculas);

        // 1. Encontrar vistas
        etIdPelicula = findViewById(R.id.et_id_pelicula_eliminar);
        btnEliminar = findViewById(R.id.btn_ejecutar_baja);

        // 2. Listener del botón ELIMINAR
        btnEliminar.setOnClickListener(v -> {
            if (validarId()) {
                // Ejecutamos la baja si el ID es válido
                ejecutarBaja();
            }
        });
    }

    private boolean validarId() {
        String sId = etIdPelicula.getText().toString().trim();
        if (sId.isEmpty()) {
            etIdPelicula.setError("Debe ingresar el ID de la película a eliminar.");
            return false;
        }
        return true;
    }

    private void ejecutarBaja() {
        final String sId = etIdPelicula.getText().toString().trim();
        final int id;

        try {
            id = Integer.parseInt(sId);
        } catch (NumberFormatException e) {
            etIdPelicula.setError("Formato de ID inválido.");
            return;
        }

        // Usamos AsyncTask para la operación de base de datos en segundo plano
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                // Obtener la instancia de la base de datos
                videosBDD db = videosBDD.getAppDatabase(getApplicationContext());
                // Llamar al nuevo método del DAO
                return db.peliculaDao().deleteById(id);
            }

            @Override
            protected void onPostExecute(Integer filasEliminadas) {
                if (filasEliminadas > 0) {
                    Toast.makeText(ActivityBajasPeliculas.this, "✅ Película ID " + id + " eliminada correctamente.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ActivityBajasPeliculas.this, "❌ No se encontró la película con ID " + id, Toast.LENGTH_LONG).show();
                }
                etIdPelicula.setText(""); // Limpiar campo después del intento
            }
        }.execute();
    }
}