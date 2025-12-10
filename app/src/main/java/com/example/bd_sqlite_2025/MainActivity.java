package com.example.bd_sqlite_2025;

import android.content.Intent;
import android.os.AsyncTask; // NECESARIO
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bd_sqlite_2025.ABCC_Peliculas.ActivityConsultasPeliculas;
import Entities.Usuario;
import db.videosBDD;

import java.lang.ref.WeakReference; // NECESARIO para AsyncTask estática

public class MainActivity extends AppCompatActivity {

    private EditText etUsuario;
    private EditText etContrasena;
    private Button btnIngresar;

    // Necesitas una ID para la vista principal para EdgeToEdge
    private int mainLayoutId = R.id.main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 1. Inicializar vistas
        etUsuario = findViewById(R.id.et_usuario);
        etContrasena = findViewById(R.id.et_contrasena);
        btnIngresar = findViewById(R.id.btn_ingresar);

        // 2. Aplicar Insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(mainLayoutId), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 3. Establecer el Listener para la autenticación
        btnIngresar.setOnClickListener(v -> intentarAutenticacion());
    }

    private void intentarAutenticacion() {
        String usuario = etUsuario.getText().toString().trim();
        String contrasena = etContrasena.getText().toString().trim();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Debe ingresar usuario y contraseña.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Ejecutar la consulta de autenticación en segundo plano, pasando 'this' (la MainActivity)
        new AutenticarTask(this, usuario, contrasena).execute();
    }

    /**
     * CLASE ANIDADA ESTÁTICA PARA LA CONSULTA A LA BASE DE DATOS
     */
    private static class AutenticarTask extends AsyncTask<Void, Void, Usuario> {

        private final String usuario;
        private final String contrasena;
        private final WeakReference<MainActivity> activityReference;

        AutenticarTask(MainActivity context, String usuario, String contrasena) {
            activityReference = new WeakReference<>(context);
            this.usuario = usuario;
            this.contrasena = contrasena;
        }

        @Override // <-- ESTE @Override ES VÁLIDO AQUÍ porque extiende AsyncTask
        protected Usuario doInBackground(Void... voids) {
            MainActivity activity = activityReference.get();
            if (activity == null) return null;

            // La base de datos siempre debe ser accedida con el contexto de la aplicación
            videosBDD db = videosBDD.getAppDatabase(activity.getApplicationContext());
            return db.usuarioDao().autenticarUsuario(usuario, contrasena);
        }

        @Override // <-- ESTE @Override ES VÁLIDO AQUÍ
        protected void onPostExecute(Usuario usuarioAutenticado) {
            MainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return; // Evitar crasheo si la Activity se cerró

            if (usuarioAutenticado != null) {
                // Autenticación Exitosa
                Toast.makeText(activity, "¡Bienvenido, " + usuarioAutenticado.getNombreUsuario() + "!", Toast.LENGTH_SHORT).show();

                // Navegación corregida al Catálogo de Películas
                Intent intent = new Intent(activity, ActivityMenuPrincipal.class);
                activity.startActivity(intent);
                activity.finish(); // Finaliza el Login
            } else {
                // Autenticación Fallida
                Toast.makeText(activity, "Usuario o contraseña incorrectos.", Toast.LENGTH_LONG).show();
                activity.etContrasena.setText(""); // Limpiar contraseña por seguridad
            }
        }
    }
}