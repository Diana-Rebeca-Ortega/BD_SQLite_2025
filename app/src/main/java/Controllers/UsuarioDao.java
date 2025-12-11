package Controllers;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Insert;
import Entities.Usuario;

@Dao
public interface UsuarioDao {

    @Query("SELECT * FROM usuario WHERE " +
            "LOWER(TRIM(NOMBRE_USUARIO)) = LOWER(TRIM(:user)) AND " +
            "LOWER(TRIM(CONTRASENA_HASH)) = LOWER(TRIM(:pass)) LIMIT 1")
    Usuario autenticarUsuario(String user, String pass);

    @Insert
    void insertarUsuario(Usuario usuario);
}