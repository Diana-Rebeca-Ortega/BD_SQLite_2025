package com.example.bd_sqlite_2025;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CatalogoFragment extends Fragment {

    public CatalogoFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // ¡Importante! Infla el nuevo layout fragment_catalogo.xml
        return inflater.inflate(R.layout.fragment_catalogo, container, false);
    }

    // Aquí es donde añadirías la lógica para cargar los datos de la base de datos
    // en la tabla (TableLayout o RecyclerView).
}