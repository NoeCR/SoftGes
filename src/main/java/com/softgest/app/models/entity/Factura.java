package com.softgest.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="facturas")
public class Factura implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String descripcion;
	
	@Temporal(TemporalType.DATE)
	@Column(name="create_fac")
	private Date createFac;
	
	@Column(name="total_factura")
	private Double facturaTotal;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Usuario usuario;
	
	@OneToMany(fetch = FetchType.LAZY, cascade= CascadeType.ALL)
	@JoinColumn(name="factura_id")
	private List<ItemFactura> items;
	

	//Con este método PrePersist antes de insertar en la BBDD la factura obtendra la fecha actual 
	@PrePersist
	public void prePersist() {
		createFac = new Date();
	}
	public Factura() {
		this.items = new ArrayList<ItemFactura>();
	}
	public List<ItemFactura> getItems() {
		return items;
	}
	public void setItems(List<ItemFactura> items) {
		this.items = items;
	}
	public Long getId() {
		return id;
	}
	public void addItem(ItemFactura item) {
		this.items.add(item);
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getCreateFac() {
		return createFac;
	}

	public void setCreateFac(Date createFac) {
		this.createFac = createFac;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
