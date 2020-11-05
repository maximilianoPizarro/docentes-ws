package com.docentes.jaxws.repository;

import java.util.List;

import javax.jws.WebMethod;

import com.docentes.model.AlumnoCursada;
import com.docentes.model.AlumnoExamenFinal;
import com.docentes.model.Materia;


public interface DocenteRepository {

   List<Materia> traerMaterias(int idDocente);

	List<AlumnoCursada> listadoAlumnosPorMateria(int idDocente, int idMateria);

    boolean cargaNotasCursada(int idDocente, int idMateria, List<AlumnoCursada> alumnosConNotas);
 
    boolean cargaNotasFinales(int idDocente, int idMateria, List<AlumnoExamenFinal> alumnosConNotas);
}