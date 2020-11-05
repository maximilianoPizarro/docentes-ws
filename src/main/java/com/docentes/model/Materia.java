package com.docentes.model;

import java.util.Date;

public class Materia {
	//idMaterias, nombre, inicioInscripcion, 
	//finInscripcion, CarrerasIdCarreras, createdAt, updatedAt, planIdPlan, formaAprobacionIdformaAprobacion
	private int idMaterias;
	private String nombre;
	private Date inicioInscripcion;
	private Date finInscripcion;
	private int CarrerasIdCarreras;
	private Date createdAt;
	private Date updatedAt;
	private int planIdPlan;
	private int formaAprobacionIdformaAprobacion;
	
	public Materia(int idMateria, String nombre, Date inicioInscripcion, Date finInscripcion, int carrerasIdCarreras,
			Date createdAt, Date updatedAt, int planIdPlan, int formaAprobacionIdformaAprobacion) {
		super();
		this.idMaterias = idMateria;
		this.nombre = nombre;
		this.inicioInscripcion = inicioInscripcion;
		this.finInscripcion = finInscripcion;
		CarrerasIdCarreras = carrerasIdCarreras;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.planIdPlan = planIdPlan;
		this.formaAprobacionIdformaAprobacion = formaAprobacionIdformaAprobacion;
	}

	public int getIdMateria() {
		return idMaterias;
	}

	public void setIdMateria(int idMateria) {
		this.idMaterias = idMateria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getInicioInscripcion() {
		return inicioInscripcion;
	}

	public void setInicioInscripcion(Date inicioInscripcion) {
		this.inicioInscripcion = inicioInscripcion;
	}

	public Date getFinInscripcion() {
		return finInscripcion;
	}

	public void setFinInscripcion(Date finInscripcion) {
		this.finInscripcion = finInscripcion;
	}

	public int getCarrerasIdCarreras() {
		return CarrerasIdCarreras;
	}

	public void setCarrerasIdCarreras(int carrerasIdCarreras) {
		CarrerasIdCarreras = carrerasIdCarreras;
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

	public int getPlanIdPlan() {
		return planIdPlan;
	}

	public void setPlanIdPlan(int planIdPlan) {
		this.planIdPlan = planIdPlan;
	}

	public int getFormaAprobacionIdformaAprobacion() {
		return formaAprobacionIdformaAprobacion;
	}

	public void setFormaAprobacionIdformaAprobacion(int formaAprobacionIdformaAprobacion) {
		this.formaAprobacionIdformaAprobacion = formaAprobacionIdformaAprobacion;
	}
	
	
	
}
