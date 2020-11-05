package com.docentes.jaxws.client;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.HttpMethod;

import com.docentes.jaxws.repository.DocenteRepositoryImpl;
import com.docentes.model.AlumnoCursada;
import com.docentes.model.AlumnoExamenFinal;
import com.docentes.model.Materia;

import org.springframework.web.bind.annotation.CrossOrigin;


public class DocentesServiceSoapImpl implements DocentesServiceSoap{
	 
    private DocenteRepositoryImpl docenteRepositoryImpl;
	 
	 public DocentesServiceSoapImpl(DocenteRepositoryImpl docenteRepositoryImpl) {
			this.docenteRepositoryImpl = docenteRepositoryImpl;
		}	 
	
	  
	  public List<Materia> traerMaterias(int idDocente) {
		return this.docenteRepositoryImpl.traerMaterias(idDocente);
	}
		 
	
		public List<AlumnoCursada> listadoAlumnosPorMateria(int idDocente, int idMateria) {
			return this.docenteRepositoryImpl.listadoAlumnosPorMateria(idDocente, idMateria);
		}
		 
	
	    public boolean cargaNotasCursada(int idDocente, int idMateria, List<AlumnoCursada> alumnosConNotas) {
	    	return this.docenteRepositoryImpl.cargaNotasCursada(idDocente, idMateria, alumnosConNotas);
		}
	 
	
	    public boolean cargaNotasFinales(int idDocente, int idMateria, List<AlumnoExamenFinal> alumnosConNotas) {
	    	return this.docenteRepositoryImpl.cargaNotasFinales(idDocente, idMateria, alumnosConNotas);
		}
}
