package com.anderjesuss.dao;

import java.util.List;

import com.anderjesuss.entity.Menu;

//Esta clase va a ser la que nos devolverá la información con el token y el tipo que tenga este

public class DtoAuthRespuesta {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer ";
    private Integer usuarioCodigo; // Agregar el ID del usuario
    private String rolDescripcion;
    private String usuarioNombre;
    private List<Menu> menus;
    private String mensajeError;
    private long tokenExpiracion;

    public DtoAuthRespuesta(String accessToken, String refreshToken, Integer usuarioCodigo, String rolDescripcion, String usuarioNombre, List<Menu> menus, long tokenExpiracion) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.usuarioCodigo = usuarioCodigo;
        this.rolDescripcion = rolDescripcion;
        this.usuarioNombre = usuarioNombre;
        this.menus = menus;
        this.tokenExpiracion = tokenExpiracion;
    }

	public long getTokenExpiracion() {
		return tokenExpiracion;
	}

	public void setTokenExpiracion(long tokenExpiracion) {
		this.tokenExpiracion = tokenExpiracion;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public DtoAuthRespuesta(String mensajeError) {
        this.mensajeError = mensajeError;
    }

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public Integer getUsuarioCodigo() {
		return usuarioCodigo;
	}

	public void setUsuarioCodigo(Integer usuarioCodigo) {
		this.usuarioCodigo = usuarioCodigo;
	}

	public String getUsuarioNombre() {
		return usuarioNombre;
	}

	public void setUsuarioNombre(String usuarioNombre) {
		this.usuarioNombre = usuarioNombre;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public String getRolDescripcion() {
		return rolDescripcion;
	}

	public void setRolDescripcion(String rolDescripcion) {
		this.rolDescripcion = rolDescripcion;
	}
	
	
}
