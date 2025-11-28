package Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Alumno {

    @PrimaryKey
    @NonNull
    public String numControl;

    @NonNull
    @ColumnInfo(name = "Nombre")
    public String nombre;

    @NonNull
    @ColumnInfo(name = "Apellido1")
    public String apellido1;

    @NonNull
    @ColumnInfo(name = "Apellido2")
    public String apellido2;

    public Alumno(@NonNull String numControl, @NonNull String nombre,
                  @NonNull String apellido1,
                  @NonNull String apellido2
    ) {
        this.numControl = numControl;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;

    }

    @NonNull
    public String getNumControl() {
        return numControl;
    }

    public void setNumControl(@NonNull String numControl) {
        this.numControl = numControl;
    }

    @NonNull
    public String getNombre() {
        return nombre;
    }

    public void setNombre(@NonNull String nombre) {
        this.nombre = nombre;
    }

    @NonNull
    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(@NonNull String apellido1) {
        this.apellido1 = apellido1;
    }

    @NonNull
    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(@NonNull String apellido2) {
        this.apellido2 = apellido2;
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "numControl='" + numControl + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido1='" + apellido1 + '\'' +
                ", apellido2='" + apellido2 + '\'' +

                '}';
    }
}
