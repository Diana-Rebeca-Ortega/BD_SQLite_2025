package com.example.bd_sqlite_2025;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout; // Importa el DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle; // Importa el componente para el icono de hamburguesa
import androidx.appcompat.widget.Toolbar; // Importa el Toolbar
import android.os.Bundle;
import android.widget.Button;
import com.example.bd_sqlite_2025.ActivityMenuPrincipal;

public class ActivityMenuPrincipal extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Button btnEmpleados;
    private Button btnCatalogo;
    private Button btnClientes;
    private Button btnAlquileres;
    private Button btnReportes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_principal);

        // 1. Configuración de la Toolbar y DrawerLayout
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // Establece el Toolbar como la ActionBar

        drawerLayout = findViewById(R.id.drawer_layout);

        // Crea el icono de hamburguesa (☰) y su funcionalidad de abrir/cerrar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
              R.string.navigation_drawer_open, // Crea estos strings en res/values/strings.xml
               R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
       toggle.syncState(); // Sincroniza el estado (muestra el icono)


        // 2. Inicializar Vistas (Conectar botones del menú con variables Java)
        btnEmpleados = findViewById(R.id.btn_empleados);
        btnCatalogo = findViewById(R.id.btn_catalogo);
        btnClientes = findViewById(R.id.btn_clientes);
        btnAlquileres = findViewById(R.id.btn_alquileres);
        btnReportes = findViewById(R.id.btn_reportes);
        // 3. Carga Inicial (Muestra la pantalla de Empleados al inicio)
        if (savedInstanceState == null) {
        //    loadFragment(new EmpleadosFragment());
        }

        // 4. Asignar Eventos Click a los Botones
        btnEmpleados.setOnClickListener(v -> {
          //  loadFragment(new EmpleadosFragment());
            drawerLayout.closeDrawers(); // Cierra el menú después de la selección
        });

        btnCatalogo.setOnClickListener(v -> {
            loadFragment(new CatalogoFragment());
            drawerLayout.closeDrawers(); // Cierra el menú después de la selección
        });

        btnClientes.setOnClickListener(v -> {
            loadFragment(new ClientesFragment());
            drawerLayout.closeDrawers();
        });
        btnAlquileres.setOnClickListener(v -> {
            loadFragment(new AlquileresFragment());
            drawerLayout.closeDrawers();
        });
        btnReportes.setOnClickListener(v -> {
            loadFragment(new ReportesFragment());
            drawerLayout.closeDrawers();
        });

    }


    private void loadFragment(Fragment fragment) {
        String title = "";
        // Determinar el título basado en el Fragmento que se carga
        if (fragment instanceof AlquileresFragment) {
            title = "GESTIÓN DE ALQUILERES";
        } else if (fragment instanceof CatalogoFragment) {
            title = "CATÁLOGO DE PELÍCULAS";
        } else if (fragment instanceof ClientesFragment) {
            title = "GESTIÓN DE CLIENTES";
        }else if (fragment instanceof ReportesFragment) {
            title = "Reportes";
        }
        // ... agrega el resto de Fragmentos

        // Establecer el título en la Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_area, fragment)
                .commit();
    }
}