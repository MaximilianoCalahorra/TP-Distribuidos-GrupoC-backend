package org.empuje_comunitario.rest_service.controller;

import org.empuje_comunitario.rest_service.dto.FiltroEventoDTO;
import org.empuje_comunitario.rest_service.service.Interfaces.IFiltroEventoService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/filtros-eventos")
@RequiredArgsConstructor
@Tag(name = "Filtros Personalizados de Eventos", 
     description = "Gestión de filtros guardados para el informe de participación en Eventos (REST)")
public class FiltroEventoController {

    // INYECCIÓN: Usamos la interfaz, no la implementación concreta
    private final IFiltroEventoService filtroEventoService; 

    @PostMapping // Mapeado a POST /api/v1/filtros-eventos
    @Operation(summary = "Guarda un NUEVO filtro de evento para el usuario autenticado.")
    @ApiResponse(responseCode = "201", description = "Filtro guardado exitosamente.")
    @ApiResponse(responseCode = "409", description = "Ya existe un filtro con el mismo nombre para este usuario.")
    @ApiResponse(responseCode = "404", description = "Usuario autenticado no encontrado (Error de seguridad).")
    public ResponseEntity<?> guardarFiltro(@RequestBody FiltroEventoDTO filtroDto) {
        try {
            // Llama al método específico de creación del Service
            FiltroEventoDTO filtroGuardado = filtroEventoService.guardarFiltro(filtroDto);
            // Retorna 201 Created (estándar para la creación exitosa)
            return ResponseEntity.status(HttpStatus.CREATED).body(filtroGuardado);
        } catch (EntityExistsException e) {
            // Retorna 409 Conflict si el filtro ya existe
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EntityNotFoundException e) {
            // Retorna 404 Not Found si falla la búsqueda del usuario (error interno/seguridad)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Manejo genérico para otros errores inesperados
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno al guardar el filtro: " + e.getMessage());
        }
    }
    
    @PutMapping // Mapeado a PUT /api/v1/filtros-eventos
    @Operation(summary = "Modifica un filtro de evento existente. Requiere el ID del filtro en el DTO.")
    @ApiResponse(responseCode = "200", description = "Filtro modificado exitosamente.")
    @ApiResponse(responseCode = "404", description = "Filtro no encontrado para el ID proporcionado.")
    @ApiResponse(responseCode = "409", description = "Ya existe otro filtro con el nuevo nombre para este usuario.")
    public ResponseEntity<?> modificarFiltro(@RequestBody FiltroEventoDTO filtroDto) {
        // Aseguramos que el DTO contenga un ID para modificar
        if (filtroDto.getId() == null) {
            return ResponseEntity.badRequest().body("Se requiere el ID del filtro para modificar.");
        }
        
        try {
            // Llama al método específico de modificación del Service
            FiltroEventoDTO filtroModificado = filtroEventoService.modificarFiltro(filtroDto);
            // Retorna 200 OK
            return ResponseEntity.ok(filtroModificado);
        } catch (EntityNotFoundException e) {
            // Retorna 404 Not Found si el filtro no existe
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (EntityExistsException e) {
            // Retorna 409 Conflict si el nuevo nombre ya está en uso
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            // Manejo genérico
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno al modificar el filtro: " + e.getMessage());
        }
    }

    @Operation(summary = "Lista los filtros personalizados del usuario logueado",
               description = "Devuelve todos los filtros guardados por el usuario actual (usando el email simulado).")
    @ApiResponse(responseCode = "200", description = "Listado exitoso")
    @GetMapping

    // listar filtros
    public ResponseEntity<List<FiltroEventoDTO>> listarFiltros() {
        
        List<FiltroEventoDTO> filtros = filtroEventoService.filtrosUsuarioLogueado();
        return ResponseEntity.ok(filtros);
    }

    @Operation(summary = "Elimina un filtro personalizado",
               description = "Elimina un filtro por su ID, verificando que pertenezca al usuario logueado.")
    @ApiResponse(responseCode = "204", description = "Filtro eliminado exitosamente (No Content)")
    @ApiResponse(responseCode = "404", description = "Filtro no encontrado")
    @ApiResponse(responseCode = "403", description = "No autorizado: El filtro no pertenece al usuario")
    @DeleteMapping("/{idFiltro}")

    // eliminar filtro
    public ResponseEntity<Void> eliminarFiltro(
            @Parameter(description = "ID del filtro a eliminar")
            @PathVariable Long idFiltro) {
        
        try {
            filtroEventoService.eliminarFiltro(idFiltro);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

