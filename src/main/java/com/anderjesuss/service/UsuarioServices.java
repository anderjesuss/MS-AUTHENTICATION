package com.anderjesuss.service;

import com.anderjesuss.entity.Usuario;

public interface UsuarioServices {
	
	public Usuario registrar(Usuario usuario);
	public Usuario buscarUsuarioPorCorreo(String correo);

}
