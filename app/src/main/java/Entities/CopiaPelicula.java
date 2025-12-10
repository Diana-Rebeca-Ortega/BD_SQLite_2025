package Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Nombre de la tabla
@Entity(tableName = "COPIA_PELICULA")
public class CopiaPelicula {

    // ID_COPIAPELICULA (SMALLINT, NOT NULL, Primary Key)
    @PrimaryKey
    @ColumnInfo(name = "ID_COPIAPELICULA")
    private int id_copiapelicula;

    // ID_CATALOGO (SMALLINT, NOT NULL)
    @ColumnInfo(name = "ID_CATALOGO")
    private int idCatalogo;

    // ID_SUCURSAL (SMALLINT, NOT NULL)
    @ColumnInfo(name = "ID_SUCURSAL")
    private int idSucursal;

    // ESTADO (VARCHAR(45), NOT NULL)
    @ColumnInfo(name = "ESTADO")
    private String estado;

    // --- Constructor ---
    public CopiaPelicula(int id_copiapelicula, int idCatalogo, int idSucursal, String estado) {
        this.id_copiapelicula = id_copiapelicula;
        this.idCatalogo = idCatalogo;
        this.idSucursal = idSucursal;
        this.estado = estado;
    }

    // --- Getters ---

    public int getId_copiapelicula() { return id_copiapelicula; }
    public int getIdCatalogo() { return idCatalogo; }
    public int getIdSucursal() { return idSucursal; }
    public String getEstado() { return estado; }

    // --- Setters ---

    public void setId_copiapelicula(int id_copiapelicula) { this.id_copiapelicula = id_copiapelicula; }
    public void setIdCatalogo(int idCatalogo) { this.idCatalogo = idCatalogo; }
    public void setIdSucursal(int idSucursal) { this.idSucursal = idSucursal; }
    public void setEstado(String estado) { this.estado = estado; }
}