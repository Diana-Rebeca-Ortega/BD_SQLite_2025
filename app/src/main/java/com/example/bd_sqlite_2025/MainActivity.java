package com.example.bd_sqlite_2025;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void abrirActivities(View v){
        //Toast.makeText(this, "Magia magia", Toast.LENGTH_LONG).show();
        Intent i = null;
        if( v.getId() == R.id.btn_altas)
            i = new Intent(this, ActivityAltas.class);
        else if( v.getId() == R.id.btn_consutlas){
            i = new Intent(this, ActivityConsultas.class);
        startActivity(i);}
        else if( v.getId() == R.id.btn_cambios){
            i = new Intent(this, ActivityCambios.class);
            startActivity(i);}
        else if( v.getId() == R.id.btn_bajas) {
            i = new Intent(this, ActivityBajas.class);
            startActivity(i);
        }else if( v.getId() == R.id.btn_ingresar) {
            i = new Intent(this, ActivityMenuPrincipal.class);
            startActivity(i);
        }

    }

}