package com.example.bd_sqlite_2025; // Asegúrate de que este paquete sea correcto

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.widget.Button;

public class ActivityMenuPrincipal extends AppCompatActivity {

    // Declara los botones del menú lateral
    private Button btnEmpleados;
    private Button btnCatalogo;
    // ... declara el resto de botones (btnClientes, btnAlquileres, etc.)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ventana_principal);

        btnEmpleados = findViewById(R.id.btn_empleados);
        btnCatalogo = findViewById(R.id.btn_catalogo);

        btnEmpleados.setOnClickListener(v -> {
       //     loadFragment(new EmpleadosFragment());
        });

        btnCatalogo.setOnClickListener(v -> {
         //   loadFragment(new CatalogoFragment());
        });


    }

       private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_area, fragment) // R.id.content_area es el FrameLayout en activity_ventana_principal.xml
                .commit();
    }
}