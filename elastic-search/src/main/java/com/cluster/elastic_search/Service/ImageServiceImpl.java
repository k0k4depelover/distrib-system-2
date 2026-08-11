package com.cluster.elastic_search.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cluster.elastic_search.Dto.ImageResponseDTO;
import com.cluster.elastic_search.Model.ImagenMD;
import com.cluster.elastic_search.Repository.ImageRepository;
import com.cluster.elastic_search.Service.Helpers.helperFuncions;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService{
        private final helperFuncions helper;
        private final ImageStorage storage;
        private final ImageService service;
        private final ImageRepository repository;
        private static final List<String> TOTAL_PERMITIDOS = List.of("image/jpeg",
            "image/png", "image/webp"
        );
        private static final long MAX_SIZE = (5* 1024 * 1024); 
        private static final Tika TIKA = new Tika();
    

    
    
    @Override
    @Transactional
    public ImageResponseDTO subirImagen(MultipartFile file, Long idUsuario) {
        
        String filename = helper.generarNombreUnico(file);
        String url = storage.guardarImagen(file, filename);

        try{
            ImagenMD entidad = new ImagenMD(LocalDateTime.now(), filename, url, idUsuario);
            repository.save(entidad);
            return ImageResponseDTO.desde(file, filename);
        }
        catch(Exception e){
            storage.eliminar(filename);
            throw e;
        }

    }
    @Override
    public void eliminarImagen(Long id, Long idUsuario) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminarImagen'");
    }

    @Override
    public List<ImageResponseDTO> buscarImagenUsuario(Long idUsuario) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarImagenUsuario'");
    }
    
}
