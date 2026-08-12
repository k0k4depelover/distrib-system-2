package com.cluster.elastic_search.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cluster.elastic_search.Config.Exceptions.ArchivoInvalidoExcepcion;
import com.cluster.elastic_search.Dto.ImageResponseDTO;
import com.cluster.elastic_search.Model.ImagenMD;
import com.cluster.elastic_search.Repository.ImageRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService{
        private final ImageStorage storage;
        private final ImageRepository repository;
        private static final List<String> TIPOS_PERMITIDOS = List.of("image/jpeg",
            "image/png", "image/webp"
        );
        private static final long MAX_SIZE = (5* 1024 * 1024); 
        private static final Tika TIKA = new Tika();
    

    @Override
    @Transactional
    public ImageResponseDTO subirImagen(MultipartFile file, Long idUsuario) {
        validarArchivo(file);
        String filename = generarNombreUnico(file); 

        try {
            String imageUrl = storage.guardarImagen(file, filename); 
            ImagenMD entidad = new ImagenMD(LocalDateTime.now(), imageUrl, filename, idUsuario);
            repository.save(entidad); 
            
            return ImageResponseDTO.desde(entidad);
        } catch (Exception e) {

            try {
                storage.eliminar(filename); 
            } catch (Exception ignored) {

            }
            
            throw e; 
        }

    }

    
    @Override
    public Boolean eliminarImagen(Long id, Long idUsuario) {
        Optional<ImagenMD> imagenDelete = repository.findByIdAndOwnerId(id, idUsuario);
        if(imagenDelete.isPresent()){
            storage.eliminar(imagenDelete.get().getNombreImagen());
            repository.delete(imagenDelete.get());
            return true;
        }
        
        return false;
    }

    @Override
    public List<ImageResponseDTO> buscarImagenesDeUsuario(Long idUsuario) {
        List<ImagenMD> listImages = repository.findByOwnerId(idUsuario);
        return listImages.stream().map(
            image -> new ImageResponseDTO(image.getFechaSubida(), image.getNombreImagen())
        ).collect(Collectors.toList());
    }



    /*
        Genera un UUID y lo castea a String.
        Obtiene el nombre original del archivo, despues obtiene el ultimo 
        punto existente en el nombre del archivo para obtener la extension del archivo.
    */
    public String generarNombreUnico(MultipartFile file){
        String originalName=  file.getOriginalFilename();
        String extension ="";
        if (originalName!= null && originalName.contains(".")){
            extension = originalName.substring(originalName.lastIndexOf("."));
        }
        return UUID.randomUUID().toString() + extension;
    }
    

    public void validarArchivo(MultipartFile file){
        if (file.isEmpty()) {
            throw new ArchivoInvalidoExcepcion("Archivo invalido");
        }
        if(file.getSize() > MAX_SIZE){
            throw new ArchivoInvalidoExcepcion("El archivo supera el tamaño permitido. (5MB) ");
        }

        if(file.getContentType() == null ||
            !TIPOS_PERMITIDOS.contains(file.getContentType())
            ){
            throw new ArchivoInvalidoExcepcion("Tipo de archivo no permitido.");
        }

        try{
            String tipoReal = TIKA.detect(file.getInputStream());
            if (!TIPOS_PERMITIDOS.contains(tipoReal)) {
                throw new ArchivoInvalidoExcepcion("El contenido de la imagen no es permitido.");
            }
        }
        catch(IOException e){
            throw new ArchivoInvalidoExcepcion("No se pudo leer el archivo");
        }
    }

}
