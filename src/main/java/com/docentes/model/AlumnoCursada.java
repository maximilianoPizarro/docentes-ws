package com.docentes.model;

import java.util.Date;

public class AlumnoCursada {
	//idalumnosCursada, datosAlumno, notaCursada, MateriasIdMaterias, recordatorio, createdAt, updatedAt, idCurso, datosDocente, MateriasIdMaterias, createdAt, updatedAt
	private int idalumnosCursada;
	private String datosAlumno;
	private int notaCursada;
	private int MateriasIdMaterias;
	private int recordatorio;
	private Date createdAt;
	private Date updatedAt;
	
	
	
	public AlumnoCursada() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AlumnoCursada(int idalumnosCursada, String datosAlumno, int notaCursada, int materiasIdMaterias, int recordatorio,
			Date createdAt, Date updatedAt) {
		super();
		this.idalumnosCursada = idalumnosCursada;
		this.datosAlumno = datosAlumno;
		this.notaCursada = notaCursada;
		MateriasIdMaterias = materiasIdMaterias;
		this.setRecordatorio(recordatorio);
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	public int getIdalumnosCursada() {
		return idalumnosCursada;
	}
	public void setIdalumnosCursada(int idalumnosCursada) {
		this.idalumnosCursada = idalumnosCursada;
	}
	public String getDatosAlumno() {
		return datosAlumno;
	}
	public void setDatosAlumno(String datosAlumno) {
		this.datosAlumno = datosAlumno;
	}
	public int getNotaCursada() {
		return notaCursada;
	}
	public void setNotaCursada(int notaCursada) {
		this.notaCursada = notaCursada;
	}
	public int getMateriasIdMaterias() {
		return MateriasIdMaterias;
	}
	public void setMateriasIdMaterias(int materiasIdMaterias) {
		MateriasIdMaterias = materiasIdMaterias;
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
	public int getRecordatorio() {
		return recordatorio;
	}
	public void setRecordatorio(int recordatorio) {
		this.recordatorio = recordatorio;
	}
	
	
}
