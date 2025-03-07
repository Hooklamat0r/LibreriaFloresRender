package com.example.libreria.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.libreria.model.Usuario;
import com.example.libreria.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca el usuario usando Optional
        Optional<Usuario> usuarioOptional = usuarioRepository.findByUsername(username);
        
        // Lanza excepción si no existe
        Usuario usuario = usuarioOptional.orElseThrow(() -> 
            new UsernameNotFoundException("Usuario no encontrado: " + username)
        );
        
        // Construye el UserDetails
        return User.builder()
            .username(usuario.getUsername())
            .password(usuario.getPassword())
            .roles(usuario.getRole())
            .build();
    }
}