package com.example.libreria.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.libreria.model.Usuario;
import com.example.libreria.repository.UsuarioRepository;

import jakarta.validation.Valid;

@Controller
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioRepository usuarioRepository, 
                            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/acceso-denegado")
    public String accesoDenegado() {
        return "acceso-denegado";
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @GetMapping("/registro")
    public String mostrarRegistro(@ModelAttribute("usuario") Usuario usuario) {
        return "registro";
    }

    @PostMapping("/registro")
public String registrarUsuario(
    @Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result, @RequestParam String confirmPassword, RedirectAttributes redirectAttributes) {
    // Validación de contraseña
    if (!usuario.getPassword().equals(confirmPassword)) {
        result.rejectValue("password", "error.usuario", "Las contraseñas no coinciden");
    }

    if (result.hasErrors()) {
        return "registro";
    }

    try {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRole("USER");
        usuarioRepository.save(usuario);
        redirectAttributes.addFlashAttribute("exito", "¡Registro exitoso! Por favor inicia sesión");
        return "redirect:/login";
        
    } catch (DataIntegrityViolationException e) {
        result.rejectValue("username", "error.usuario", "El nombre de usuario ya existe");
        return "registro";
    }
}
}