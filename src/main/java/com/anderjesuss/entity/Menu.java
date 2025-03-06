package com.anderjesuss.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_menu")
public class Menu {
	@Id
	@Column(name = "cod_menu")
	private Integer codigomenu;
	private String nombre;
	private String url;
	private String icon;
	
	
	@ManyToMany
	@JoinTable(name = "tb_menu_rol",
			joinColumns = @JoinColumn(name="cod_menu",referencedColumnName ="cod_menu"),
			inverseJoinColumns = @JoinColumn(name="cod_rol",referencedColumnName = "cod_rol"))
	private List<Rol> listaRoles;
	
}
