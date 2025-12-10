package com.example.bd_sqlite_2025.ABCC_Peliculas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.bd_sqlite_2025.R;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Entities.Pelicula;
import db.videosBDD;

public class ModificarPeliculaDialog extends DialogFragment {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    // UI Elements
    private EditText etTitulo, etDirector, etAlquiler, etPrecioVenta, etStockTotal;
    private Spinner spinnerCategoria;
    private Button btnGuardar, btnCancelar;

    // Variables de la Película
    private int peliculaIdAEditar = -1; // Guardaremos el ID de la película que vamos a modificar
    private Pelicula peliculaOriginal; // Para mantener la referencia a la Película original

    public ModificarPeliculaDialog() {
        // Constructor vacío requerido
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Recuperar el ID de la Película del Bundle
        if (getArguments() != null) {
            peliculaIdAEditar = getArguments().getInt("PELICULA_ID", -1);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_registrar_pelicula, container, false);

        if (getDialog() != null) {
            getDialog().setTitle("MODIFICAR PELICULA (ID: " + peliculaIdAEditar + ")");
        }

        // Inicializar vistas (reutilizando el layout dialog_registrar_pelicula.xml)
        etTitulo = view.findViewById(R.id.et_titulo);
        // ... (Inicializa todos los demás EditText y el Spinner) ...
        etDirector = view.findViewById(R.id.et_director);
        etAlquiler = view.findViewById(R.id.et_alquiler);
        etPrecioVenta = view.findViewById(R.id.et_precio_venta);
        etStockTotal = view.findViewById(R.id.et_stock_total);
        spinnerCategoria = view.findViewById(R.id.spinner_categoria);

        btnGuardar = view.findViewById(R.id.btn_guardar);
        btnCancelar = view.findViewById(R.id.btn_cancelar);

        // 2. Cargar los datos de la base de datos
        if (peliculaIdAEditar != -1) {
            cargarDatosPelicula(peliculaIdAEditar);
        } else {
            Toast.makeText(requireContext(), "Error: No se encontró el ID de la película a modificar.", Toast.LENGTH_LONG).show();
            dismiss();
        }

        // 3. Listener para GUARDAR (Ahora será ACTUALIZAR)
        btnGuardar.setOnClickListener(v -> {
            if (validarCampos()) {
                actualizarPelicula();
            }
        });

        // 4. Listener para CANCELAR
        btnCancelar.setOnClickListener(v -> dismiss());

        return view;
    }

    // --- MÉTODOS CLAVE ---

    // Este método debe obtener la película por ID y llenar los campos.
    private void cargarDatosPelicula(int id) {
        executorService.execute(() -> {
            videosBDD db = videosBDD.getAppDatabase(requireContext());

            // Suponemos que tienes un método en PeliculaDao para obtener una Pelicula por ID
            // Si no lo tienes, debes agregarlo: @Query("SELECT * FROM PELICULA WHERE ID_PELICULA = :id")
            Pelicula pelicula = db.peliculaDao().getPeliculaById(id);

            requireActivity().runOnUiThread(() -> {
                if (pelicula != null) {
                    this.peliculaOriginal = pelicula; // Guardamos la referencia original

                    // Llenar los EditTexts y Spinner con los datos de 'pelicula'
                    etTitulo.setText(pelicula.getTitulo());
                    etDirector.setText(pelicula.getDirector());
                    etAlquiler.setText(String.valueOf(pelicula.getAlquilerDiario()));
                    etPrecioVenta.setText(String.valueOf(pelicula.getCosteVenta()));
                    etStockTotal.setText(String.valueOf(pelicula.getStockTotal()));
                    // TO-DO: Implementar lógica para seleccionar el valor correcto en 'spinnerCategoria'
                } else {
                    Toast.makeText(requireContext(), "Película no encontrada en la base de datos.", Toast.LENGTH_LONG).show();
                    dismiss();
                }
            });
        });
    }

    // Este método construye la Pelicula actualizada y llama a la operación UPDATE
    private void actualizarPelicula() {
        if (peliculaOriginal == null || !validarCampos()) return;

        // 1. Obtener y convertir TODOS los nuevos valores de la UI
        try {
            String nuevoTitulo = etTitulo.getText().toString();

            // Asumimos que obtienes el valor del Spinner (Ajusta el índice si es necesario)
            String nuevaCategoria = spinnerCategoria.getSelectedItem().toString();

            String nuevoDirector = etDirector.getText().toString();

            // Conversión de valores numéricos
            double nuevoAlquiler = Double.parseDouble(etAlquiler.getText().toString());
            double nuevoCosteVenta = Double.parseDouble(etPrecioVenta.getText().toString());
            int nuevoStock = Integer.parseInt(etStockTotal.getText().toString());

            // 2. Crear la Película Actualizada, pasando los 8 argumentos
            Pelicula peliculaActualizada = new Pelicula(
                    // 1. ID_PELICULA (int) - CRÍTICO, se mantiene el ID original
                    peliculaOriginal.getId_pelicula(),

                    // 2. TITULO (String) - Nuevo valor
                    nuevoTitulo,

                    // 3. CATEGORIA (String) - Nuevo valor
                    nuevaCategoria,

                    // 4. DIRECTOR (String) - Nuevo valor
                    nuevoDirector,

                    // 5. ALQUILER_DIARIO (double) - Nuevo valor
                    nuevoAlquiler,

                    // 6. COSTE_VENTA (double) - Nuevo valor
                    nuevoCosteVenta,

                    // 7. STOCK_TOTAL (int) - Nuevo valor
                    nuevoStock,

                    // 8. ID_SUCURSAL (int) - Se mantiene el original (ya que no se edita en la UI)
                    peliculaOriginal.getIdSucursal()
            );

            // 3. Ejecutar la operación de UPDATE en segundo plano
            executorService.execute(() -> {
                videosBDD db = videosBDD.getAppDatabase(requireContext());
                db.peliculaDao().update(peliculaActualizada);

                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(requireContext(), "✅ Película " + nuevoTitulo + " actualizada.", Toast.LENGTH_SHORT).show();
                    dismiss();
                });
            });

        } catch (NumberFormatException e) {
            Toast.makeText(requireContext(), "Error: Asegúrate de que los campos numéricos sean correctos.", Toast.LENGTH_LONG).show();
        }
    }

    // Debes reutilizar o copiar tu método validarCampos() aquí
    private boolean validarCampos() {
        // ... (Tu lógica de validación aquí) ...
        return true;
    }
}