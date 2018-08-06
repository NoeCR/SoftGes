package com.softgest.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="productos")
public class Producto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	private Double precio;
	
	@Temporal(TemporalType.DATE)
	@Column(name="create_pro")
	private Date createPro;
	
	
	//añadir campo comentario, La clase comentario tendra campos id,texto,valoración(entre 1-5) y usuario que realiza el comentario
	//añadir campo categoria, la clase categoria tendra: id,nombre
	
	@PrePersist
	public void prePersist() {
		createPro = new Date();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public Date getCreatePro() {
		return createPro;
	}
	public void setCreatePro(Date createPro) {
		this.createPro = createPro;
	}

	
	
}
