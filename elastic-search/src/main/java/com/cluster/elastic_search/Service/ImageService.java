package com.cluster.elastic_search.Service;

import java.util.List;


import org.springframework.web.multipart.MultipartFile;

import com.cluster.elastic_search.Dto.ImageResponseDTO;
import com.cluster.elastic_search.Model.ImagenMD;

public interface ImageService {
    ImageResponseDTO subirImagen(MultipartFile file, Long idUsuario);
    Boolean eliminarImagen(Long id, Long idUsuario);
    List<ImageResponseDTO> buscarImagenesDeUsuario(Long idUsuario);
    ImagenMD confirmarYExtraer (Long id, Long idUsuario);
}
