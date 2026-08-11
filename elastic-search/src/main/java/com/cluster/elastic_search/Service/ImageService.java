package com.cluster.elastic_search.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cluster.elastic_search.Dto.ImageResponseDTO;

public interface ImageService {
    ImageResponseDTO subirImagen(MultipartFile file, Long idUsuario);
    void eliminarImagen(Long id, Long idUsuario);
    List<ImageResponseDTO> buscarImagenUsuario(Long idUsuario);
}
