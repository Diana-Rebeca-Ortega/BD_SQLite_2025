package com.example.bd_sqlite_2025;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;

import db.EscuelaBD;
import Entities.Alumno;

public class ActivityCambios extends Activity {
    EditText cajaNumControl, cajaNombre, cajaAP1, cajaAP2;
    Button btnGuardarCambios;

    private Alumno alumnoEncontrado;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambios);
        cajaNumControl = findViewById(R.id.caja_numcontrol);
        cajaNombre = findViewById(R.id.cajanombre);
        cajaAP1 = findViewById(R.id.cajaap1);
        cajaAP2 = findViewById(R.id.cajaap2);
        btnGuardarCambios = findViewById(R.id.btn_guardar_cambios);
        btnGuardarCambios.setOnClickListener(this::guardarCambios);

        // 2. Bloquear campos de edición al inicio
        habilitarCampos(false);
    }

    // Método auxiliar para habilitar/deshabilitar campos (modificado para el botón de Guardar)
    private void habilitarCampos(boolean habilitar) {
        cajaNombre.setEnabled(habilitar);
        cajaAP1.setEnabled(habilitar);
        cajaAP2.setEnabled(habilitar);
        btnGuardarCambios.setEnabled(habilitar); // Habilita o deshabilita el botón
    }

    private void limpiarCampos() {
        cajaNumControl.setText("");
        cajaNombre.setText("");
        cajaAP1.setText("");
        cajaAP2.setText("");
    }

    /**
     * Se llama desde el botón "Traer datos" (debes añadir android:onClick="buscarAlumno" a tu btn_busqueda en el XML).
     * Busca el alumno por NC en la BD.
     */
    public void buscarAlumno(View v) {
        final String nc = cajaNumControl.getText().toString().trim();
        if (nc.isEmpty()) {
            Toast.makeText(this, "Ingrese el Número de Control a buscar.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Ejecución en hilo de fondo (OBLIGATORIO para operaciones Room)
        new Thread(new Runnable() {
            @Override
            public void run() {
                EscuelaBD bd = EscuelaBD.getAppDatabase(getApplicationContext());
                alumnoEncontrado = bd.alumnoDAO().obtenerAlumnoPorNumControl(nc);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (alumnoEncontrado != null) {
                            // Cargar datos en los campos
                            cajaNombre.setText(alumnoEncontrado.nombre);
                            cajaAP1.setText(alumnoEncontrado.apellido1);
                            cajaAP2.setText(alumnoEncontrado.apellido2);

                            // Habilitar la edición y el botón de guardar
                            habilitarCampos(true);
                            Toast.makeText(ActivityCambios.this, "Alumno encontrado. Puede editar los campos.", Toast.LENGTH_SHORT).show();
                        } else {
                            // Limpiar campos y deshabilitar edición
                            limpiarCampos();
                            habilitarCampos(false);
                            Toast.makeText(ActivityCambios.this, "Alumno no encontrado.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * Se llama desde el botón "GUARDAR CAMBIOS".
     * Actualiza el objeto Alumno y lo guarda en la BD.
     */
    public void guardarCambios(View v) {
        // Validación básica
        if (alumnoEncontrado == null || !btnGuardarCambios.isEnabled()) {
            Toast.makeText(this, "Debe buscar un alumno válido antes de guardar.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 1. Actualizar los datos del objeto 'alumnoEncontrado' con los valores de las cajas de texto
        alumnoEncontrado.nombre = cajaNombre.getText().toString().trim();
        alumnoEncontrado.apellido1 = cajaAP1.getText().toString().trim();
        alumnoEncontrado.apellido2 = cajaAP2.getText().toString().trim();

        // El numControl se usa como llave primaria para la actualización por el método @Update

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EscuelaBD bd = EscuelaBD.getAppDatabase(getApplicationContext());
                    // 2. Ejecutar la actualización con el objeto modificado (usa el método @Update)
                    bd.alumnoDAO().actualizarAlumno(alumnoEncontrado);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ActivityCambios.this, "Cambios guardados con éxito.", Toast.LENGTH_SHORT).show();
                            // Resetear la interfaz después del éxito
                            habilitarCampos(false);
                            limpiarCampos();
                            alumnoEncontrado = null; // Reiniciar el objeto
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() ->
                            Toast.makeText(ActivityCambios.this, "ERROR al actualizar: " + e.getMessage(), Toast.LENGTH_LONG).show()
                    );
                }
            }
        }).start();
    }
}