package com.example.forohub.service;

import com.example.forohub.domain.Curso;
import com.example.forohub.domain.Topico;
import com.example.forohub.domain.Usuario;
import com.example.forohub.domain.dto.DatosActualizacionTopico;
import com.example.forohub.domain.dto.DatosRegistroTopico;
import com.example.forohub.repository.CursoRepository;
import com.example.forohub.repository.TopicoRepository;
import com.example.forohub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;

    public Topico registrarTopico(DatosRegistroTopico datos) {
        if (topicoRepository.existsByTituloIgnoreCaseAndMensajeIgnoreCase(datos.titulo(), datos.mensaje())) {
            throw new IllegalStateException("Ya existe un tópico con el mismo título y mensaje.");
        }

        Usuario autor = usuarioRepository.findById(datos.autorId())
                .orElseThrow(() -> new IllegalArgumentException("Autor no encontrado."));
        Curso curso = cursoRepository.findById(datos.cursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado."));

        Topico topico = new Topico();
        topico.setTitulo(datos.titulo());
        topico.setMensaje(datos.mensaje());
        topico.setAutor(autor);
        topico.setCurso(curso);
        topico.setStatus("ABIERTO");

        return topicoRepository.save(topico);
    }

    public Page<Topico> listarTopicos(Pageable pageable, String nombreCurso, Integer anio) {
        if (nombreCurso != null && anio != null) {
            return topicoRepository.findByCursoNombreAndFechaCreacionYear(nombreCurso, anio, pageable);
        } else {
            return topicoRepository.findAll(pageable);
        }
    }

    public Topico obtenerTopicoPorId(Long id) {
        return topicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tópico no encontrado."));
    }

    public Topico actualizarTopico(Long id, DatosActualizacionTopico datos) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tópico no encontrado."));

        if (datos.titulo() != null && datos.mensaje() != null &&
            topicoRepository.existsByTituloIgnoreCaseAndMensajeIgnoreCaseAndIdNot(datos.titulo(), datos.mensaje(), id)) {
            throw new IllegalStateException("Ya existe otro tópico con el mismo título y mensaje.");
        }

        if (datos.titulo() != null) {
            topico.setTitulo(datos.titulo());
        }
        if (datos.mensaje() != null) {
            topico.setMensaje(datos.mensaje());
        }
        if (datos.status() != null) {
            topico.setStatus(datos.status());
        }

        return topicoRepository.save(topico);
    }

    public void eliminarTopico(Long id) {
        if (!topicoRepository.existsById(id)) {
            throw new IllegalArgumentException("Tópico no encontrado.");
        }
        topicoRepository.deleteById(id);
    }
}

