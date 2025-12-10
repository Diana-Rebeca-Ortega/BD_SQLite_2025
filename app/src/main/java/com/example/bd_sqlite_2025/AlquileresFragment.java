package com.example.bd_sqlite_2025;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AlquileresFragment extends Fragment {

    public AlquileresFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla el nuevo layout fragment_alquileres.xml
        return inflater.inflate(R.layout.fragment_alquileres, container, false);
    }
}