package com.example.bd_sqlite_2025.ABCC_Peliculas;

import Entities.Pelicula;

public interface PeliculaClickListener {
    // Este método se llamará en ActivityConsultas cuando el usuario haga clic en una película.
    void onPeliculaClick(Pelicula pelicula);
}