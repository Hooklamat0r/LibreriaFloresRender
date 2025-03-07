package com.example.libreria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.libreria.model.Libro;
import com.example.libreria.repository.LibroRepository;

import jakarta.validation.Valid;

@Controller
public class LibroController {

    private final LibroRepository libroRepository;

    public LibroController(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    // Listado principal de libros
    @GetMapping("/")
    public String listarLibros(Model model) {
        model.addAttribute("libros", libroRepository.findAll());
        return "index";
    }

    // Formulario de agregar
    @GetMapping("/agregar")
    public String mostrarFormularioAgregar(Model model) {
        model.addAttribute("libro", new Libro());
        return "add-libro";
    }

    // Guardar nuevo libro
    @PostMapping("/guardar")
    public String guardarLibro(@Valid @ModelAttribute Libro libro, BindingResult result, RedirectAttributes redirectAttributes) {
    
        if(result.hasErrors()) {
            return "add-libro";
        }
    
        try {
            libroRepository.save(libro);
            redirectAttributes.addFlashAttribute("exito", "Libro guardado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el libro");
        }
        
        return "redirect:/";
    }

    // Formulario de edición
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Libro libro = libroRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("libro", libro);
        return "editar-libro";
    }

    // Actualizar libro existente
    @PostMapping("/actualizar")
    public String actualizarLibro(@Valid @ModelAttribute Libro libro, BindingResult result, RedirectAttributes redirectAttributes) {
    
        if(result.hasErrors()) {
            return "editar-libro";
        }
    
        try {
            libroRepository.save(libro);
            redirectAttributes.addFlashAttribute("exito", "Libro actualizado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el libro");
        }

        return "redirect:/";
    }

    // Eliminar libro
    @GetMapping("/eliminar/{id}")
    public String eliminarLibro(@PathVariable Long id) {
        libroRepository.deleteById(id);
        return "redirect:/";
    }

    // Búsqueda de libros
    @GetMapping("/buscar")
    public String buscarLibros(@RequestParam(required = false) String query, Model model) {
        if (query == null || query.trim().isEmpty()) {
            model.addAttribute("mostrarFormulario", true); // Mostrar formulario vacío
            return "buscar-libro";
        }
        
        model.addAttribute("resultados", libroRepository.buscarPorQuery(query));
        model.addAttribute("query", query); // Mantener el término buscado
        return "buscar-libro";
    }
}