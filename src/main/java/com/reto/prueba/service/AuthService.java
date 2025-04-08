package com.reto.prueba.service;

import com.reto.prueba.dto.auth.JwtResponse;
import com.reto.prueba.dto.auth.LoginRequest;
import com.reto.prueba.dto.auth.RegisterRequest;
import com.reto.prueba.exception.UserAlreadyExistsException;
import com.reto.prueba.model.Usuario;
import com.reto.prueba.repository.UsuarioRepository;
import com.reto.prueba.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Usuario usuario = usuarioRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String jwt = jwtTokenUtil.generateToken(usuario);

        return JwtResponse.builder()
                .token(jwt)
                .build();
    }

    public JwtResponse registerUser(RegisterRequest registerRequest) {
        // Verificar si el usuario ya existe
        if (usuarioRepository.existsByUsername(registerRequest.getUsername())) {
            throw new UserAlreadyExistsException("El nombre de usuario ya está en uso");
        }

        if (usuarioRepository.existsByEmail(registerRequest.getEmail())) {
            throw new UserAlreadyExistsException("El correo electrónico ya está en uso");
        }

        // Crear nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(registerRequest.getUsername());
        usuario.setEmail(registerRequest.getEmail());
        usuario.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        usuario.setNombreCompleto(registerRequest.getNombreCompleto());

        Usuario savedUsuario = usuarioRepository.save(usuario);

        // Generar token JWT
        String jwt = jwtTokenUtil.generateToken(savedUsuario);

        return JwtResponse.builder()
                .token(jwt)
                .build();
    }
}