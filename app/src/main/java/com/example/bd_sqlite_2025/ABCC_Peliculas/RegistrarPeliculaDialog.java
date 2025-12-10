package com.example.bd_sqlite_2025.ABCC_Peliculas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.DialogFragment;
import com.example.bd_sqlite_2025.R;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import db.videosBDD; // Usando el nombre de clase correcto
import Entities.Pelicula;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// ELIMINAR imports: androidx.lifecycle.Observer, androidx.recyclerview.widget.RecyclerView, Adapters.PeliculaAdapter

public class RegistrarPeliculaDialog extends DialogFragment {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private EditText etTitulo, etDirector, etAlquiler, etPrecioVenta, etStockTotal;
    private Spinner spinnerCategoria;
    private Button btnGuardar, btnCancelar;

    // ELIMINADAS: private RecyclerView recyclerView;
    // ELIMINADAS: private PeliculaAdapter adapter;
    // ELIMINADAS: private videosBDD db;

    public RegistrarPeliculaDialog() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_registrar_pelicula, container, false);
        if (getDialog() != null) {
            getDialog().setTitle("REGISTRAR NUEVA PELICULA");
        }

        // ELIMINADA: db = VideosDatabase.getAppDatabase(getApplicationContext());

        etTitulo = view.findViewById(R.id.et_titulo);
        etDirector = view.findViewById(R.id.et_director);
        etAlquiler = view.findViewById(R.id.et_alquiler);
        etPrecioVenta = view.findViewById(R.id.et_precio_venta);
        etStockTotal = view.findViewById(R.id.et_stock_total);
        spinnerCategoria = view.findViewById(R.id.spinner_categoria);

        btnGuardar = view.findViewById(R.id.btn_guardar);
        btnCancelar = view.findViewById(R.id.btn_cancelar);

        btnCancelar.setOnClickListener(v -> dismiss());

        btnGuardar.setOnClickListener(v -> {
            if (validarCampos()) {
                registrarPelicula();
            }
        });

        return view;
    }

    // ... (Método validarCampos() correcto) ...

    private void registrarPelicula() {
        // 1. Obtener valores de los campos
        String titulo = etTitulo.getText().toString();
        String director = etDirector.getText().toString();
        String categoria = spinnerCategoria.getSelectedItem().toString();
        String sAlquiler = etAlquiler.getText().toString();
        String sPrecioVenta = etPrecioVenta.getText().toString();
        String sStockTotal = etStockTotal.getText().toString();

        if (titulo.isEmpty() || sAlquiler.isEmpty() || sStockTotal.isEmpty()) {
            Toast.makeText(getContext(), "Por favor, completa los campos obligatorios.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double alquiler = Double.parseDouble(sAlquiler);
            double precioVenta = Double.parseDouble(sPrecioVenta);
            int stockTotal = Integer.parseInt(sStockTotal);
            int idSucursal = 1;

            final Pelicula nuevaPelicula = new Pelicula(
                    titulo, categoria, director, alquiler, precioVenta, stockTotal, idSucursal
            );
            insertarEnBaseDeDatos(nuevaPelicula);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Error en formato numérico (Alquiler, Venta o Stock).", Toast.LENGTH_LONG).show();
        }
    }

    private void insertarEnBaseDeDatos(final Pelicula pelicula) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // USANDO LA CLASE CORRECTA: videosBDD
                    videosBDD db = videosBDD.getAppDatabase(requireContext());
                    db.peliculaDao().insert(pelicula);

                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(requireContext(), "✅ Película " + pelicula.getTitulo() + " insertada correctamente.", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }
                    });
                } catch (Exception e) {
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(requireContext(), "❌ Error al insertar: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    private boolean validarCampos() {
        boolean esValido = true;

        // --- 1. Validar campos de texto vacíos ---
        if (etTitulo.getText().toString().trim().isEmpty()) {
            etTitulo.setError("El título es obligatorio");
            esValido = false;
        } else {
            etTitulo.setError(null); // Limpiar error si está bien
        }

        if (etDirector.getText().toString().trim().isEmpty()) {
            etDirector.setError("El director es obligatorio");
            esValido = false;
        } else {
            etDirector.setError(null);
        }

        // --- 2. Validar el Spinner (Categoría) ---
        if (spinnerCategoria.getSelectedItemPosition() == 0) { // Asume que la posición 0 es "Seleccione una opción"
            Toast.makeText(getContext(), "Debe seleccionar una Categoría", Toast.LENGTH_SHORT).show();
            esValido = false;
        }

        // --- 3. Validar campos numéricos vacíos ---
        if (etAlquiler.getText().toString().trim().isEmpty()) {
            etAlquiler.setError("Alquiler es obligatorio");
            esValido = false;
        }
        if (etPrecioVenta.getText().toString().trim().isEmpty()) {
            etPrecioVenta.setError("Precio de venta es obligatorio");
            esValido = false;
        }
        if (etStockTotal.getText().toString().trim().isEmpty()) {
            etStockTotal.setError("Stock es obligatorio");
            esValido = false;
        }

        // --- 4. Validar formato numérico (si no están vacíos) ---
        try {
            if (!etAlquiler.getText().toString().trim().isEmpty()) {
                Double.parseDouble(etAlquiler.getText().toString());
                etAlquiler.setError(null);
            }
            if (!etPrecioVenta.getText().toString().trim().isEmpty()) {
                Double.parseDouble(etPrecioVenta.getText().toString());
                etPrecioVenta.setError(null);
            }
            if (!etStockTotal.getText().toString().trim().isEmpty()) {
                Integer.parseInt(etStockTotal.getText().toString());
                etStockTotal.setError(null);
            }
        } catch (NumberFormatException e) {
             }

        return esValido;
    }
}