package com.anderjesuss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.anderjesuss.entity.Usuario;

public interface UserRepository extends JpaRepository<Usuario, Integer> {
	
	//Método para poder buscar un usuario mediante su nombre
	//spring data JPA
	public Usuario findByCorreo(String correo);
	
	//Método para poder verificar si un usuario existe en nuestra base de datos
    public Boolean existsByCorreo(String correo);
}
