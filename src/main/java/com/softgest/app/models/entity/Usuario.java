package com.softgest.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.context.annotation.Scope;

@Entity
@Table(name="users")
@Scope("session")
public class Usuario implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "El nick de usuario no puede estar vacio.")
	@Size(min = 4, max = 30, message = "El nick de usuario debe tener un minimo de 4 caracteres")
	@Column(length = 30, unique=true)
	private String username;
	
	@NotEmpty(message = "Se requiere una contraseña para poder acceder.")
	@Column(length = 60)
	private String password;
	
	private boolean enabled;
	
	@OneToMany(fetch = FetchType.LAZY, cascade= CascadeType.ALL)
	@JoinColumn(name="user_id")
	private List<Role> roles = new ArrayList<Role>();
	
	@OneToMany(mappedBy="usuario",fetch=FetchType.LAZY, cascade=CascadeType.ALL)	
	private List<Factura> facturas;
	
	// Esta variable sera pasada a la entidad Factura cuando se cree la factura, por lo tanto con la anotacion @Transien indicamos a Hibernate que no la tenga encuenta para persistirn en BBDD
	@Transient
	private List<ItemFactura> items;
	
	
	public Usuario() {
		this.enabled = true;
		this.facturas = new ArrayList<Factura>();
		//this.items = new ArrayList<ItemFactura>();
	}

	public List<ItemFactura> getItems() {
		return items;
	}

	public void setItems(List<ItemFactura> items) {
		this.items = items;
	}
	
	public void addItem(ItemFactura item) {
		items.add(item);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public void setRole(Role rol) {
		roles.add(rol);
	}

	public List<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}
	
	public void addFactura(Factura factura) {
		facturas.add(factura);
	}
}
