package com.cluster.elastic_search.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.cluster.elastic_search.Dto.ImageResponseDTO;
import com.cluster.elastic_search.Model.ImagenMD;

public interface ImageService {
    ImageResponseDTO subirImagen(MultipartFile file, Long idUsuario);
    Boolean eliminarImagen(Long id, Long idUsuario);
    List<ImageResponseDTO> buscarImagenesDeUsuario(Long idUsuario);
    Optional<ImagenMD> confirmarYExtraer (Long id, Long idUsuario);
}
