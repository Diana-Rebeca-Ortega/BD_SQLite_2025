package Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;
// Nombre de la tabla
@Entity(tableName = "PELICULA")
public class Pelicula {

    // ID_PELICULA (SMALLINT, NOT NULL, Primary Key)
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_PELICULA")
    private int id_pelicula;

    // TITULO (VARCHAR(45), NOT NULL)
    @ColumnInfo(name = "TITULO")
    private String titulo;

    // CATEGORIA (VARCHAR(45), NOT NULL)
    @ColumnInfo(name = "CATEGORIA")
    private String categoria;

    // DIRECTOR (VARCHAR(45), Nulos=Sí) - Puede ser nulo
    @ColumnInfo(name = "DIRECTOR")
    private String director;

    // ALQUILER_DIARIO (DECIMAL(10, 2), NOT NULL)
    @ColumnInfo(name = "ALQUILER_DIARIO")
    private double alquilerDiario;

    // COSTE_VENTA (DECIMAL(10, 2), NOT NULL)
    @ColumnInfo(name = "COSTE_VENTA")
    private double costeVenta;

    // STOCK_TOTAL (SMALLINT, NOT NULL)
    @ColumnInfo(name = "STOCK_TOTAL")
    private int stockTotal;

    // ID_SUCURSAL (SMALLINT, Nulos=Sí) - Puede ser nulo, asumimos 0 si no está presente
    @ColumnInfo(name = "ID_SUCURSAL")
    private int idSucursal;

    // --- Constructor (necesario para Room) ---
    public Pelicula(int id_pelicula, String titulo, String categoria, String director,
                    double alquilerDiario, double costeVenta, int stockTotal, int idSucursal) {
        this.id_pelicula = id_pelicula;
        this.titulo = titulo;
        this.categoria = categoria;
        this.director = director;
        this.alquilerDiario = alquilerDiario;
        this.costeVenta = costeVenta;
        this.stockTotal = stockTotal;
        this.idSucursal = idSucursal;
    }
    // --- CONSTRUCTOR 2: Sin ID (Usado para INSERTAR nuevos registros) ---
    @Ignore
    public Pelicula(String titulo, String categoria, String director,
                    double alquilerDiario, double costeVenta, int stockTotal, int idSucursal) {
        // El ID_PELICULA será asignado por la base de datos (autoGenerate)
        this.titulo = titulo;
        this.categoria = categoria;
        this.director = director;
        this.alquilerDiario = alquilerDiario;
        this.costeVenta = costeVenta;
        this.stockTotal = stockTotal;
        this.idSucursal = idSucursal;
    }

    // --- Getters (Mínimo necesario para Room) ---

    public int getId_pelicula() { return id_pelicula; }
    public String getTitulo() { return titulo; }
    public String getCategoria() { return categoria; }
    public String getDirector() { return director; }
    public double getAlquilerDiario() { return alquilerDiario; }
    public double getCosteVenta() { return costeVenta; }
    public int getStockTotal() { return stockTotal; }
    public int getIdSucursal() { return idSucursal; }

    // --- Setters (Útiles para Room y manipulación) ---

    public void setId_pelicula(int id_pelicula) { this.id_pelicula = id_pelicula; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setDirector(String director) { this.director = director; }
    public void setAlquilerDiario(double alquilerDiario) { this.alquilerDiario = alquilerDiario; }
    public void setCosteVenta(double costeVenta) { this.costeVenta = costeVenta; }
    public void setStockTotal(int stockTotal) { this.stockTotal = stockTotal; }
    public void setIdSucursal(int idSucursal) { this.idSucursal = idSucursal; }

}