package com.example.bd_sqlite_2025;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ClientesFragment extends Fragment {

    public ClientesFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla el nuevo layout fragment_clientes.xml
        return inflater.inflate(R.layout.fragment_clientes, container, false);
    }

    // Aquí iría la lógica para interactuar con la interfaz de clientes (ej. click en AÑADIR)
}