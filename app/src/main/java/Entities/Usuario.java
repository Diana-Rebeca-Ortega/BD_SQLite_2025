package Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Mapea a la tabla USUARIO de tu base de datos
@Entity(tableName = "usuario")
public class Usuario {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_USUARIO")
    private int idUsuario;

    @ColumnInfo(name = "NOMBRE_USUARIO")
    private String nombreUsuario;

    @ColumnInfo(name = "TIPO_USUARIO")
    private String tipoUsuario;

    @ColumnInfo(name = "CONTRASENA_HASH") // El campo de la contraseña en tu tabla
    private String contrasenaHash;

    // Constructor (necesario para Room)
    public Usuario(String nombreUsuario, String tipoUsuario, String contrasenaHash) {
        this.nombreUsuario = nombreUsuario;
        this.tipoUsuario = tipoUsuario;
        this.contrasenaHash = contrasenaHash;
    }

    // --- Getters y Setters ---

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getContrasenaHash() {
        return contrasenaHash;
    }

    public void setContrasenaHash(String contrasenaHash) {
        this.contrasenaHash = contrasenaHash;
    }
}