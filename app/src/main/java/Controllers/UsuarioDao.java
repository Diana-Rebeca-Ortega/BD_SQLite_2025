package Controllers;

import androidx.room.Dao;
import androidx.room.Query;

import Entities.Usuario;

@Dao
public interface UsuarioDao {

    @Query("SELECT * FROM usuario WHERE NOMBRE_USUARIO = :user AND CONTRASENA_HASH = :pass LIMIT 1")
    Usuario autenticarUsuario(String user, String pass);

}