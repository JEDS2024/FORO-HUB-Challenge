package com.example.forohub.controller;

import com.example.forohub.domain.Topico;
import com.example.forohub.domain.dto.DatosActualizacionTopico;
import com.example.forohub.domain.dto.DatosListadoTopico;
import com.example.forohub.domain.dto.DatosRegistroTopico;
import com.example.forohub.service.TopicoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @PostMapping
    @Transactional
    public ResponseEntity registrarTopico(@RequestBody @Valid DatosRegistroTopico datos) {
        try {
            Topico topico = topicoService.registrarTopico(datos);
            return ResponseEntity.status(HttpStatus.CREATED).body(topico);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listarTopicos(
            @PageableDefault(size = 10, sort = {"fechaCreacion"}) Pageable pageable,
            @RequestParam(required = false) String nombreCurso,
            @RequestParam(required = false) Integer anio
    ) {
        Page<DatosListadoTopico> topicos = topicoService.listarTopicos(pageable, nombreCurso, anio)
                .map(DatosListadoTopico::new);
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity detallarTopico(@PathVariable Long id) {
        try {
            Topico topico = topicoService.obtenerTopicoPorId(id);
            return ResponseEntity.ok(new DatosListadoTopico(topico));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizacionTopico datos) {
        try {
            Topico topico = topicoService.actualizarTopico(id, datos);
            return ResponseEntity.ok(new DatosListadoTopico(topico));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        try {
            topicoService.eliminarTopico(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

