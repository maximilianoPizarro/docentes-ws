package com.docentes.model;

import java.util.Date;

public class AlumnoExamenFinal {
	//idInscriptosExamen, ExamenesidExamenes, datosAlumno, nota, asistencia, recordatorio, createdAt, updatedAt
	private int idInscriptosExamen;
	private String datosAlumno;
	private int nota;
	private int asistencia;
	private int ExamenesidExamenes;
	private int recordatorio;
	private Date createdAt;
	private Date updatedAt;	
	
	public AlumnoExamenFinal(int idInscriptosExamen, String datosAlumno, int nota, int asistencia,
			int examenesidExamenes, int recordatorio, Date createdAt, Date updatedAt) {
		super();
		this.idInscriptosExamen = idInscriptosExamen;
		this.datosAlumno = datosAlumno;
		this.nota = nota;
		this.asistencia = asistencia;
		ExamenesidExamenes = examenesidExamenes;
		this.recordatorio = recordatorio;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}



	public AlumnoExamenFinal() {
		super();
		// TODO Auto-generated constructor stub
	}



	public int getIdInscriptosExamen() {
		return idInscriptosExamen;
	}



	public void setIdInscriptosExamen(int idInscriptosExamen) {
		this.idInscriptosExamen = idInscriptosExamen;
	}



	public String getDatosAlumno() {
		return datosAlumno;
	}



	public void setDatosAlumno(String datosAlumno) {
		this.datosAlumno = datosAlumno;
	}



	public int getNota() {
		return nota;
	}



	public void setNota(int nota) {
		this.nota = nota;
	}



	public int getAsistencia() {
		return asistencia;
	}



	public void setAsistencia(int asistencia) {
		this.asistencia = asistencia;
	}



	public int getExamenesidExamenes() {
		return ExamenesidExamenes;
	}



	public void setExamenesidExamenes(int examenesidExamenes) {
		ExamenesidExamenes = examenesidExamenes;
	}



	public int getRecordatorio() {
		return recordatorio;
	}



	public void setRecordatorio(int recordatorio) {
		this.recordatorio = recordatorio;
	}



	public Date getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}



	public Date getUpdatedAt() {
		return updatedAt;
	}



	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
		
}
