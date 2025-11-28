package controllers;
import  androidx.room.Insert;

import java.util.List;

import entities.Alumno;

    public interface  AlumnoDAO{
        //A------ALTAS-----
        @Insert
        public void agregarAlumno(Alumno alumno);


        //*************************BAJAS*-----------------------
        @Delete
        public  void eliminarAlumno (Alumno alumno);

        @Query("DELETE FROM alumno WHERE numControl=:nc")
        public void eliminarAlumnoPorNumControl(String nc);


        //*******************************CAMBIOS///////////
        @Update
        public void actualizarAlumno(Alumno alumno);

        //@Query("UPDATE alumno SET nombre=:n, primerAp=:pa WHERE numControl=:nc")
        @Query("UPDATE alumno SET nombre=:n WHERE numControl=:nc")
        public void actualizarAlumno(String n, String pa, String nc);

        //-------------------------CONSULTAS----------------------
        @Query("SELECT * FROM alumno")
        public List<Alumno> mostrarTodos();

        @Query("SELECT * FROM alumno WHERE nombre=:n")
        public List<Alumno> mostrarPorNombre(String n);





}
