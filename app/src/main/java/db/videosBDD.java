package db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// --- Imports de DAOs (Controladores) ---
import Controllers.PeliculaDao;
import Controllers.CopiaPeliculaDao;
import Controllers.UsuarioDao;

// --- Imports de Entities ---
import Entities.Pelicula;
import Entities.CopiaPelicula;
import Entities.Usuario; // <--- NUEVO IMPORT

// 1. Añadimos Usuario.class a la lista de Entities
@Database(entities = {Pelicula.class, CopiaPelicula.class, Usuario.class}, version = 2)
public abstract class videosBDD extends RoomDatabase {

    private static videosBDD INSTANCE;

    // 2. Definimos el método abstracto para obtener el DAO de Peliculas
    public abstract PeliculaDao peliculaDao();

    // 3. Definimos el método abstracto para obtener el DAO de Copias de Peliculas
    public abstract CopiaPeliculaDao copiaPeliculaDao();

    public abstract UsuarioDao usuarioDao();

    public static videosBDD getAppDatabase(Context context){
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            videosBDD.class,
                            "BD_Videos"
                    )
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return INSTANCE;
    }

    public static void destroyInstance(){INSTANCE = null;}
}