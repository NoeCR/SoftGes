package com.softgest.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="facturas_items")
public class ItemFactura implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer cantidad;
	
	@Column(name="total_linea")
	private Double lineaTotal;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="producto_id")
	private Producto producto;
	
	public ItemFactura() {
		
	}
	public ItemFactura(Producto producto) {		
		this.cantidad = 1;
		this.producto = producto;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public void addCantidad() {
		this.cantidad++;
	}
	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	public Double getLineaTotal() {
		return lineaTotal;
	}

	public void setLineaTotal(Double lineaTotal) {
		this.lineaTotal = lineaTotal;
	}

	@Override
	public String toString() {
		return "ItemFactura [id=" + id + ", cantidad=" + cantidad + ", producto=" + producto + "]";
	}
	
}
