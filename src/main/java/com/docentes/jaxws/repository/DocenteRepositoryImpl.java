package com.docentes.jaxws.repository;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.*;
import org.springframework.jdbc.core.JdbcTemplate;

import com.docentes.dao.AlumnosCursadaDao;
import com.docentes.dao.AlumnosExamenFinalDao;
import com.docentes.dao.MateriasDao;
import com.docentes.model.AlumnoCursada;
import com.docentes.model.AlumnoExamenFinal;
import com.docentes.model.Materia;

public class DocenteRepositoryImpl implements DocenteRepository {
	
   public MateriasDao daoMaterias;   

   public AlumnosCursadaDao daoAlumnoCursada;

   public AlumnosExamenFinalDao daoAlumnoExamen;
     
   public DocenteRepositoryImpl() {
	super();
}

public List<Materia> traerMaterias(int idDocente){	   
	   
	   List<Materia> listaMat = null;
	   try {
	   		   listaMat = daoMaterias.findByDocente(idDocente);
	   } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	   
	 /*  
	   String SQL_QUERY = "SELECT * FROM inscripciones.materias inner join curso on materias.idMaterias = curso.MateriasIdMaterias where JSON_UNQUOTE(datosDocente->\"$.id\") = " + idDocente +" ;";
	   
       List<Materia> listaMat = null;
       try (Connection con = DataSource.getConnection();
           PreparedStatement pst = con.prepareStatement( SQL_QUERY );
           ResultSet rs = pst.executeQuery();) {
       		listaMat = new ArrayList<>();
               Materia m;
               while ( rs.next() ) {
                   m = new Materia();
                   m.setIdMateria(rs.getInt( 1 ) );
                   m.setNombre(rs.getNString( 2 ) );
                   
                   
                   listaMat.add( m );
                   System.out.print("MTARIA ------" + m.getIdMateria());    
               }
               
               
               //CIERRO CONEXION
         //      DataSource.closeConnection();
               
   	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}      	   
	   */
	return listaMat;
	
}

public List<AlumnoCursada> listadoAlumnosPorMateria(int idDocente, int idMateria){
	//idalumnosCursada, datosAlumno, notaCursada, MateriasIdMaterias, recordatorio, createdAt, updatedAt
	   
	   List<AlumnoCursada> listaCursadas = null;
	   try {
		   listaCursadas = daoAlumnoCursada.findByPorDocenteYMateria(idDocente, idMateria);
	   } catch (Exception e) {
			// TODO Auto-generated catch block
		e.printStackTrace();
	}   
	
	   
	String SQL_QUERY ="SELECT idalumnosCursada,datosAlumno,notaCursada, alumnoscursada.MateriasIdMaterias  FROM inscripciones.alumnoscursada " + 
	   		"inner join curso on alumnoscursada.MateriasIdMaterias = curso.MateriasIdMaterias " + 
	   		"where alumnoscursada.MateriasIdMaterias = " + idMateria  + " and JSON_UNQUOTE(datosDocente->\"$.id\") = "+ idDocente + " ;" ;
	   
	   System.out.print(SQL_QUERY);
	   
       List<AlumnoCursada> listaAlumno = null;
       try (Connection con = DataSource.getConnection();
           PreparedStatement pst = con.prepareStatement( SQL_QUERY );
           ResultSet rs = pst.executeQuery();) {
    	   listaAlumno = new ArrayList<>();
    	   AlumnoCursada a;
               while ( rs.next() ) {
                   a = new AlumnoCursada();
                   a.setIdalumnosCursada(rs.getInt( 1 ) );
                   a.setDatosAlumno(rs.getNString( 2 ) );
                   a.setNotaCursada(rs.getInt( 3 ) );
                   a.setMateriasIdMaterias(rs.getInt( 4 ) );                   
                   
                   listaAlumno.add( a );
                   
                   System.out.print("Alumno ->" + a.getDatosAlumno());
               }
               
               
               //CIERRO CONEXION
         //      DataSource.closeConnection();
               
   	} catch (SQLException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}      	   
		   
		return listaAlumno;
		
}

public boolean cargaNotasCursada(int idDocente, int idMateria, List<AlumnoCursada> alumnosConNotas){
	
	boolean resultado = false;
	
	//updateNotaAlumnoCursada
	   
   Iterator<AlumnoCursada> itAlumnos = alumnosConNotas.iterator();
	   
   try {
	   while (itAlumnos.hasNext()) {
		   
		   AlumnoCursada a = itAlumnos.next();
		   System.out.print(a.getDatosAlumno());
		   daoAlumnoCursada.updateNotaAlumnoCursada(a);
		   resultado=true;
	   }   
   }catch (Exception e) {
	   System.out.print(e.getMessage());
	   resultado=false;
}
      
   return resultado;
		

}
 
    public boolean cargaNotasFinales(int idDocente, int idMateria, List<AlumnoExamenFinal> alumnosConNotas){
    	boolean resultado = false;    	
    	
    	//updateNotaAlumnoCursada
    	   
       Iterator<AlumnoExamenFinal> itAlumnos = alumnosConNotas.iterator();
    	   
       try {
    	   while (itAlumnos.hasNext()) {
    		   
    		   AlumnoExamenFinal a = itAlumnos.next();
    		   System.out.print(a.getDatosAlumno());
    		   daoAlumnoExamen.updateNotaAlumnoExamenFinal(a);
    		   resultado=true;
    	   }   
       }catch (Exception e) {
    	   System.out.print(e.getMessage());
    	   resultado=false;
    }
          
       return resultado;
		
    
    }

}