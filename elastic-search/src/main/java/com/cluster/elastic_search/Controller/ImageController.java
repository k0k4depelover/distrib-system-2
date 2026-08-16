package com.cluster.elastic_search.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import com.cluster.elastic_search.Dto.ImageResponseDTO;
import com.cluster.elastic_search.Service.ImageService;


import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/image")
public class ImageController {
    
    private final ImageService imageService;

    public ImageController(ImageService imageService){
        this.imageService = imageService;
    }

    @GetMapping
    public ResponseEntity<List<ImageResponseDTO>> buscarImagenesDeUsuario(@PathVariable Long idUsuario){
        List<ImageResponseDTO> listaUsuarios = imageService.buscarImagenesDeUsuario(idUsuario);
        return ResponseEntity.ok(listaUsuarios);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageResponseDTO> subirImagen (@RequestParam("file") MultipartFile file,
     Authentication authentication ) // El parametro Authentication hace que Spring llame una una clase interna llamada AuthenticationPrincipalArgumentResolver
                                    //  que se encarga de obtener la identidad del usuario y se llama posteriormente para obtener informacion del usuario y poder 
                                    // ser procesada por los servicios.
    {
        
        Long idUsuario = (Long) authentication.getPrincipal();

        ImageResponseDTO imagePost = imageService.subirImagen(file, idUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(imagePost);
    }

    @DeleteMapping
    public ResponseEntity<?> eliminarImagen(@PathVariable Long id, Authentication authentication){
        Long idUsuario = (Long) authentication.getPrincipal();
        Boolean stateDetele = imageService.eliminarImagen(id, idUsuario);
        if (!stateDetele) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        
    }   
    

}
