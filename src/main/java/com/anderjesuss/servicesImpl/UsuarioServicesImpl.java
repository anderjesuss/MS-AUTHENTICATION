package com.anderjesuss.servicesImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.anderjesuss.entity.Rol;
import com.anderjesuss.entity.Usuario;
import com.anderjesuss.repository.UserRepository;
import com.anderjesuss.service.UsuarioServices;

@Service
public class UsuarioServicesImpl implements UsuarioServices {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	@Override
	public Usuario registrar(Usuario usuario) {

	    validarDuplicados(usuario);

	    Rol r = new Rol();
	    r.setId(3);
	    String password = encoder.encode(usuario.getPassword());
	    usuario.setPassword(password);
	    usuario.setRol(r);
	    usuario.setEstado(true);

	    // Guardar en la base de datos
	    Usuario usuarioRegistrado = repo.save(usuario);

	    return usuarioRegistrado;
	}
	
	@Override
	public Usuario buscarUsuarioPorCorreo(String correo) {
		return repo.findByCorreo(correo);
	}

	private void validarDuplicados(Usuario usuario) {
        if (repo.existsByCorreo(usuario.getCorreo())) {
            throw new DuplicateKeyException("El correo electrónico ya está registrado");
        }
    }
	
}
