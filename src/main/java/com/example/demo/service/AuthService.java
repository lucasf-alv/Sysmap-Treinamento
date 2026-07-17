package com.example.demo.service;

import com.example.demo.Config.JwtProperties;
import com.example.demo.Enums.PerfilNome;
import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.model.Usuario;
import com.example.demo.repository.PerfilRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.AuthenticatedUsuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final JwtProperties jwtProperties;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    private final UserRepository userRepository;
    private final PerfilRepository perfilRepository;


    public AuthResponse login(LoginRequest loginRequest) {
        try {

            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    loginRequest.email().trim(),
                                    loginRequest.password()
                            )
                    );


            AuthenticatedUsuario au =
                    (AuthenticatedUsuario) authentication.getPrincipal();


            return buildToken(au.getUsuario());


        } catch (Exception e) {

            log.error("Erro ao autenticar usuário", e);

            throw new UnauthorizedException(
                    "Email ou senha inválidos."
            );
        }
    }


    public void register(RegisterRequest request) {


        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException(
                    "Email já cadastrado"
            );
        }


        Usuario usuario = Usuario.builder()
                .email(request.email())
                .password(
                        passwordEncoder.encode(
                                request.password()
                        )
                )
                .perfil(
                        perfilRepository.findByNome(PerfilNome.USER)
                                .orElseThrow(
                                        () -> new RuntimeException(
                                                "Perfil USER não encontrado"
                                        )
                                )
                )
                .build();


        userRepository.save(usuario);
    }


    private AuthResponse buildToken(Usuario usuario) {

        return new AuthResponse(
                "Bearer",
                jwtTokenService.generateToken(usuario),
                jwtProperties.expirationsMs(),
                usuario.getPerfil().getNome().name()
        );
    }
}