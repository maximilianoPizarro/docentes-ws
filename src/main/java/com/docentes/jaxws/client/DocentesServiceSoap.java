package com.docentes.jaxws.client;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.docentes.model.AlumnoCursada;
import com.docentes.model.AlumnoExamenFinal;
import com.docentes.model.Materia;
import javax.ws.rs.HttpMethod;

import org.springframework.web.bind.annotation.CrossOrigin;


@WebService
public interface DocentesServiceSoap {
	
	  @WebMethod
    List<Materia> traerMaterias(int idDocente);
	 
	@WebMethod
	List<AlumnoCursada> listadoAlumnosPorMateria(int idDocente, int idMateria);
	 
    @WebMethod
    boolean cargaNotasCursada(int idDocente, int idMateria, List<AlumnoCursada> alumnosConNotas);
 
    @WebMethod
    boolean cargaNotasFinales(int idDocente, int idMateria, List<AlumnoExamenFinal> alumnosConNotas);
    
    
}
