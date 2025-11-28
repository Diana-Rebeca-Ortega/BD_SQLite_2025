package com.example.bd_sqlite_2025;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import db.EscuelaBD;
import Entities.Alumno;

public class ActivityAltas extends Activity {

    EditText cajaNumControl, cajaNombre, cajaap1 ,cajaap2 ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altas);

        cajaNumControl = findViewById(R.id.caja_num_control);
        cajaNombre = findViewById(R.id.caja_nombre);
        cajaap1 = findViewById(R.id.cajaAP1);
        cajaap2 = findViewById(R.id.cajaAP2);

    }

    public void agregarAlumno(View v){
        String nc = cajaNumControl.getText().toString();
        String n = cajaNombre.getText().toString();
        String a1 = cajaap1.getText().toString();
        String a2 = cajaap2.getText().toString();
        if (nc.isEmpty() || n.isEmpty() || a1.isEmpty()) {
            Toast.makeText(this, "Todos los campos deben estar llenos.", Toast.LENGTH_SHORT).show();
            return;
        }
        Alumno alumno = new Alumno(nc, n , a1,a2);
        EscuelaBD bd = EscuelaBD.getAppDatabase(getBaseContext());
        //Looper y Handler
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                bd.alumnoDAO().agregarAlumno(alumno);
                Log.i("MSJ=>", "Insercion Correcta!!!");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(getBaseContext(), "Insercion correcta",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                } catch (android.database.sqlite.SQLiteConstraintException e) {
                    // 3. FALLO (NC DUPLICADO): LLAMAR AL HILO PRINCIPAL para mostrar el error
                    runOnUiThread(() -> {
                        Toast.makeText(ActivityAltas.this, "ERROR: El Número de Control ya existe en la BD.", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    });
                } catch (Exception e) {
                    // 4. CUALQUIER OTRO FALLO: LLAMAR AL HILO PRINCIPAL
                    runOnUiThread(() -> {
                        Toast.makeText(ActivityAltas.this, "ERROR desconocido al guardar: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    });
                }
            }
        }).start();
    }
}


