package Controllers;

import androidx.room.Dao;
import androidx.room.Insert;
import Entities.CopiaPelicula;

@Dao
public interface CopiaPeliculaDao {
    @Insert
    void insert(CopiaPelicula copia);
    // ... más consultas
}