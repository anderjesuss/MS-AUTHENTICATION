package com.anderjesuss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anderjesuss.dao.DtoAuthRespuesta;
import com.anderjesuss.dao.DtoLogin;
import com.anderjesuss.entity.Menu;
import com.anderjesuss.entity.Usuario;
import com.anderjesuss.security.ConstantesSeguridad;
import com.anderjesuss.security.JwtGenerador;
import com.anderjesuss.servicesImpl.MenuServicesImpl;
import com.anderjesuss.servicesImpl.UsuarioServicesImpl;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth/")
public class AuthController {
	@Autowired
    private AuthenticationManager authenticationManager;

	@Autowired
    private JwtGenerador jwtGenerador;
	
	@Autowired
	private MenuServicesImpl servicioMenu;
	
	@Autowired
	private UsuarioServicesImpl servicioUsuario;
	
    //Método para poder logear un usuario y obtener un token
	@PostMapping("login")
	public ResponseEntity<DtoAuthRespuesta> login(@RequestBody DtoLogin dtoLogin) {
	    try {
	        // Obtener el usuario por correo electrónico
	        Usuario usuario = servicioUsuario.buscarUsuarioPorCorreo(dtoLogin.getCorreo());
	        
	        if (usuario == null) {
	            return new ResponseEntity<>(new DtoAuthRespuesta("Usuario no encontrado"), HttpStatus.NOT_FOUND);
	        }
	        
	        // Autenticación del usuario
	        Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(dtoLogin.getCorreo(), dtoLogin.getPassword()));
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        
	        if (!usuario.getEstado()) {
	            return new ResponseEntity<>(new DtoAuthRespuesta("Usuario inactivo"), HttpStatus.FORBIDDEN);
	        }

	        // Generar el token JWT de acceso
	        String accessToken = jwtGenerador.generarToken(authentication); // Generación del access token
	        
	        // Generar el refresh token utilizando el correo del usuario
	        String refreshToken = jwtGenerador.generarRefreshToken(usuario.getCorreo()); // Generación del refresh token

	        // Verifica que el refreshToken no sea null antes de responder
	        if (refreshToken == null) {
	            return new ResponseEntity<>(new DtoAuthRespuesta("Error generando refresh token"), HttpStatus.INTERNAL_SERVER_ERROR);
	        }

	        // Obtener los menús del usuario
	        List<Menu> menus = servicioMenu.menusUsuario(dtoLogin.getCorreo());

	        // Retornar el token de acceso, refresh token, los datos del usuario y los menús
	        DtoAuthRespuesta respuesta = new DtoAuthRespuesta(
	                accessToken, refreshToken, usuario.getId(), usuario.getRol().getDescripcion(), 
	                usuario.getFirstName(), menus, ConstantesSeguridad.JWT_EXPIRATION_TOKEN);
	        return new ResponseEntity<>(respuesta, HttpStatus.OK);
	        
	    } catch (BadCredentialsException e) {
	        return new ResponseEntity<>(new DtoAuthRespuesta("Credenciales incorrectas"), HttpStatus.UNAUTHORIZED);
	    } catch (UsernameNotFoundException e) {
	        return new ResponseEntity<>(new DtoAuthRespuesta("Usuario no encontrado"), HttpStatus.NOT_FOUND);
	    } catch (Exception e) {
	        return new ResponseEntity<>(new DtoAuthRespuesta("Error en la autenticación"), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	@PostMapping("refreshToken")
	public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
	    try {
	        // Obtener el refreshToken del Map
	        String refreshToken = request.get("refreshToken");

	        // Verificar si el refreshToken está presente
	        if (refreshToken == null || refreshToken.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                    .body("El refresh token no se proporciona o está vacío.");
	        }

	        // Validar el refresh token
	        if (jwtGenerador.validarRefreshToken(refreshToken)) {
	            // Generar un nuevo access token a partir del refresh token válido
	            String newAccessToken = jwtGenerador.generarAccessTokenDesdeRefreshToken(refreshToken);
	            return ResponseEntity.ok().body(Map.of("accessToken", newAccessToken));
	        } else {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                    .body("El refresh token es inválido o ha expirado.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Ocurrió un error al refrescar el token.");
	    }
	}
	
	/*
	// Método para registrarse
    @PostMapping("register")
    public ResponseEntity<?> registrar(@RequestBody Usuario bean) {
        try {
            Usuario obj = servicioUsuario.registrar(bean);
            return new ResponseEntity<>(obj, HttpStatus.CREATED);
        } catch (DuplicateKeyException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al registrar el usuario.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    //Se usa para mostrar los menus del usuario logueado
    @GetMapping("menus/{login}")
    public List<Menu> menus(@PathVariable("login") String vLogin) {
    	return servicioMenu.menusUsuario(vLogin);
    }

}
