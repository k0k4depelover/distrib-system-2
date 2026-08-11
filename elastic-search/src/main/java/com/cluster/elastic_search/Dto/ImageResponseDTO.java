package com.cluster.elastic_search.Dto;

import java.time.LocalDateTime;

import com.cluster.elastic_search.Model.ImagenMD;


public class ImageResponseDTO {

    private LocalDateTime fechaSubida;
    private String imageUrl;
    private String nombreOriginal;
    private Long idUsuario;

    public ImageResponseDTO(LocalDateTime fechaSubida, String imageUrl, String nombreOriginal, Long idUsuario) {
        this.fechaSubida=fechaSubida;
        this.imageUrl= imageUrl;
        this.nombreOriginal= nombreOriginal;
        this.idUsuario= idUsuario;
    }

    public static ImageResponseDTO desde(ImagenMD image){
        return new ImageResponseDTO(
            image.getFechaSubida(), 
            image.getImageUrl(), 
            image.getNombreOriginal(),
            image.getIdUsuario()
        );
    }

}
