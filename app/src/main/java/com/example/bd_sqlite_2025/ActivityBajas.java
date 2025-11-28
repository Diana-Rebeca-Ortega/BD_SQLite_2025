package com.example.bd_sqlite_2025;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import db.EscuelaBD;
// No se necesita Entities.Alumno para la baja por ID

public class ActivityBajas extends Activity {

    // Solo necesitamos la caja de texto para el Número de Control
    EditText cajaNumControl;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Asegúrate de que tienes un layout llamado activity_bajas.xml
        setContentView(R.layout.activity_bajas);

        // 1. Vinculación: Usar el ID del EditText definido en activity_bajas.xml
        // Este ID debe ser diferente al de activity_altas.xml, ej: caja_baja_numcontrol
        cajaNumControl = findViewById(R.id.caja_baja_numcontrol);
    }

    /**
     * Método para eliminar un Alumno por su Número de Control (NC).
     * Este método se llama desde el android:onClick del botón Eliminar.
     */
    public void eliminarAlumno(View v){
        // 1. Obtener el dato clave
        final String nc = cajaNumControl.getText().toString().trim();

        if (nc.isEmpty()) {
            Toast.makeText(this, "Ingrese el Número de Control para eliminar.", Toast.LENGTH_SHORT).show();
            return;
        }

        final EscuelaBD bd = EscuelaBD.getAppDatabase(getApplicationContext());

        // 2. Ejecutar la operación en un HILO DE FONDO (es OBLIGATORIO)
        new Thread(new Runnable() {
            @Override
            public void run() {
                int filasEliminadas = 0;
                try {
                    // Llamar a la operación de BAJA en el DAO
                    // IMPORTANTE: Este método elimina la fila cuyo numControl coincida.

                    // Nota: Si el método en el DAO devuelve un 'int' (filas afectadas), es mejor.
                    // Si es 'void' (como el que tienes), se asume éxito si no hay excepción.
                    bd.alumnoDAO().eliminarAlumnoPorNumControl(nc);

                    Log.i("MSJ=>", "Baja solicitada para NC: " + nc);

                    // Si el proceso llega aquí sin excepción, es que la operación de BD fue válida.
                    // (Aunque el alumno no existiera, la query es válida).

                } catch (Exception e) {
                    Log.e("DB_ERROR", "Falla al eliminar: " + e.getMessage());
                }

                // 3. Mostrar el resultado en el HILO PRINCIPAL (UI Thread)
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // El Toast se muestra asumiendo que el DAO hizo su trabajo.
                        Toast.makeText(ActivityBajas.this, "Baja procesada para NC: " + nc,
                                Toast.LENGTH_LONG).show();

                        // Limpiar la caja después de la operación
                        cajaNumControl.setText("");
                    }
                });
            }
        }).start();
    }
}