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
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.sqlite.db.SupportSQLiteDatabase;

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
                    .addCallback(roomCallback)
                    .build();
        }

        return INSTANCE;
    }
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // Ejecutar la inserción en segundo plano
            new PopulateDbAsyncTask(INSTANCE).execute();
        }
    };
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private final UsuarioDao usuarioDao;

        PopulateDbAsyncTask(videosBDD db) {
            usuarioDao = db.usuarioDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Inserta el usuario de prueba (nombreUsuario, tipoUsuario, contrasenaHash)
            Usuario usuarioAdmin = new Usuario("diana", "ADMIN", "diana123");
            usuarioDao.insertarUsuario(usuarioAdmin);
            return null;
        }
    }
    public static void destroyInstance(){INSTANCE = null;}
}