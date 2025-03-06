package com.anderjesuss.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_user")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "email")
	private String correo;
	@Column(name = "password")
	private String password;
	@Column(name = "profile_image")
	private String profileImg;
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	@Column(name = "estado")
	private Boolean estado;
	
	// Para el Rol
	@ManyToOne
	@JoinColumn(name = "idrol")
	private Rol rol;

}
