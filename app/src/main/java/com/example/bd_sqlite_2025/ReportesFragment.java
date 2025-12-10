package com.example.bd_sqlite_2025;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ReportesFragment extends Fragment {

    public ReportesFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla el nuevo layout fragment_reportes.xml
        return inflater.inflate(R.layout.fragment_reportes, container, false);
    }

    // Aquí iría la lógica para manejar el click en 'Generar Reporte' y la selección de Radio Buttons.
}