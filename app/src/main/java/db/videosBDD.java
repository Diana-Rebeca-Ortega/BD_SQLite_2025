package db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import Controllers.PeliculaDao;
import Controllers.CopiaPeliculaDao;
import Entities.Pelicula;
import Entities.CopiaPelicula;

@Database(entities = {Pelicula.class, CopiaPelicula.class}, version = 2)
public abstract class videosBDD extends RoomDatabase {

    private static videosBDD INSTANCE;

    // Definimos el método abstracto para obtener el DAO de Peliculas
    public abstract PeliculaDao peliculaDao();

    // Definimos el método abstracto para obtener el DAO de Copias de Peliculas
    public abstract CopiaPeliculaDao copiaPeliculaDao();

    public static videosBDD getAppDatabase(Context context){
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            videosBDD.class,           // Usamos la nueva clase
                            "BD_Videos"                     // Nuevo nombre de la base de datos
                    )
                    // Agregamos esto para simplificar durante el desarrollo. En producción, usa migraciones.
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return INSTANCE;
    }

    public static void destroyInstance(){INSTANCE = null;}
}