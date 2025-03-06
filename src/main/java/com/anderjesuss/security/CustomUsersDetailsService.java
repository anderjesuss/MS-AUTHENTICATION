package com.anderjesuss.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import com.anderjesuss.entity.Usuario;
import com.anderjesuss.repository.UserRepository;
import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUsersDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = userRepo.findByCorreo(correo);
        //validar
        if(usuario == null)
        	 new UsernameNotFoundException("Usuario no encontrado");
        
        Set<GrantedAuthority> data = new HashSet<GrantedAuthority>();
        data.add(new SimpleGrantedAuthority(usuario.getRol().getDescripcion()));
        
        return new User(usuario.getCorreo(), usuario.getPassword(), data);
        
    }
}
