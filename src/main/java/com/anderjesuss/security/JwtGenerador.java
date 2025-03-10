package com.anderjesuss.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtGenerador {

	// Método para crear un token por medio de la authentication
	public String generarToken(Authentication authentication) {

		String username = authentication.getName();
		Date tiempoActual = new Date();
		Date expiracionToken = new Date(tiempoActual.getTime() + ConstantesSeguridad.JWT_EXPIRATION_TOKEN);

		// Linea para generar el token
		String token = Jwts.builder() // Construimos un token
				.setSubject(username) // Establecemos el nombre de usuario que está iniciando sesión
				.setIssuedAt(new Date()) // Establecemos la fecha de emisión del token en el momento actual
				.setExpiration(expiracionToken) // Establecemos la fecha de caducidad del token
				.signWith(SignatureAlgorithm.HS512, ConstantesSeguridad.JWT_FIRMA) /*
																					 * Utilizamos este método para
																					 * firmar nuestro token y de esta
																					 * manera evitar la manipulación o
																					 * modificación de este
																					 */
				.compact(); // Este método finaliza la construcción del token y lo convierte en una cadena
							// compacta
		return token;
	}

	public String generarRefreshToken(String correo) {
	    // Verificar que el correo no sea nulo
	    if (correo == null || correo.isEmpty()) {
	        throw new IllegalArgumentException("El correo es nulo o vacío");
	    }
	    
	    Date tiempoActual = new Date();
	    Date expiracionRefreshToken = new Date(tiempoActual.getTime() + ConstantesSeguridad.JWT_EXPIRATION_REFRESH_TOKEN);

	    // Generación del refresh token
	    return Jwts.builder()
	            .setSubject(correo)  // Correo del usuario como subject
	            .setIssuedAt(tiempoActual)  // Fecha de emisión
	            .setExpiration(expiracionRefreshToken)  // Fecha de expiración del refresh token
	            .signWith(SignatureAlgorithm.HS512, ConstantesSeguridad.JWT_FIRMA)  // Firma con el algoritmo y la clave secreta
	            .compact();  // Retorna el token generado
	}

	// Método para validar el token
	public Boolean validarToken(String token) {
		try {
			// Validación del token por medio de la firma que contiene el String
			// token(token)
			// Si son idénticas validara el token o caso contrario saltara la excepción de
			// abajo
			Jwts.parser().setSigningKey(ConstantesSeguridad.JWT_FIRMA).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			throw new AuthenticationCredentialsNotFoundException("Jwt ah expirado o esta incorrecto");
		}
	}

	// Método para validar el refresh token
	public Boolean validarRefreshToken(String refreshToken) {
		try {
			Jwts.parser().setSigningKey(ConstantesSeguridad.JWT_FIRMA).parseClaimsJws(refreshToken);
			return true;
		} catch (Exception e) {
			throw new AuthenticationCredentialsNotFoundException("El refresh token ha expirado o es incorrecto");
		}
	}

	// Método para extraer un Username apartir de un token
	public String obtenerUsernameDeJwt(String token) {
		Claims claims = Jwts.parser() // El método parser se utiliza con el fin de analizar el token
				.setSigningKey(ConstantesSeguridad.JWT_FIRMA)// Establece la clave de firma, que se utiliza para
																// verificar la firma del token
				.parseClaimsJws(token) // Se utiliza para verificar la firma del token, apartir del String "token"
				.getBody(); /*
							 * Obtenemos el claims(cuerpo) ya verificado del token el cual contendrá la
							 * información de nombre de usuario, fecha de expiración y firma del token
							 */
		return claims.getSubject(); // Devolvemos el nombre de usuario
	}
	
	// Método para generar un nuevo access token usando un refresh token válido
    public String generarAccessTokenDesdeRefreshToken(String refreshToken) {
        if (validarRefreshToken(refreshToken)) {
            Claims claims = Jwts.parser().setSigningKey(ConstantesSeguridad.JWT_FIRMA).parseClaimsJws(refreshToken).getBody();
            String username = claims.getSubject();
            
            Date tiempoActual = new Date();
            Date expiracionToken = new Date(tiempoActual.getTime() + ConstantesSeguridad.JWT_EXPIRATION_TOKEN);

            return Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(tiempoActual)
                    .setExpiration(expiracionToken)
                    .signWith(SignatureAlgorithm.HS512, ConstantesSeguridad.JWT_FIRMA)
                    .compact();
        } else {
            throw new AuthenticationCredentialsNotFoundException("Refresh token no válido");
        }
    }

}
