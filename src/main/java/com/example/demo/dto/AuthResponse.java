package com.example.demo.dto;

public record AuthResponse(String tokeType, String  accessToken, String refreshToken, String perfil) {
}
