package Controllers;
import androidx.room.Insert;
import androidx.room.Dao;
import androidx.room.Insert;
import Entities.Pelicula;
import androidx.room.Query;
import androidx.lifecycle.LiveData;
import java.util.List;
import androidx.room.Update;
@Dao
public interface PeliculaDao {
    @Insert
    void insert(Pelicula pelicula);
    @Insert
    void insertAll(Pelicula... peliculas);
    @Query("SELECT * FROM PELICULA ORDER BY TITULO ASC")
    LiveData<List<Pelicula>> getAllPeliculas();

    @Query("SELECT * FROM PELICULA WHERE ID_PELICULA = :id")
    Pelicula getPeliculaById(int id);
    @Update
    void update(Pelicula pelicula);
}