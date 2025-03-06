package com.anderjesuss.security;

public class ConstantesSeguridad {
    public static final long JWT_EXPIRATION_TOKEN = 7200000; //equivale a 5 min, donde 60000 = a 1 min
    public static final long JWT_EXPIRATION_REFRESH_TOKEN = 604800000;  // Tiempo de expiración del refreshToken (en ms, aquí 24 horas)
    public static final String JWT_FIRMA = "firma";
}
