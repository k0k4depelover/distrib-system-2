package com.cluster.elastic_search.Dto;

import java.time.LocalDateTime;

import com.cluster.elastic_search.Model.ImagenMD;


public class ImageResponseDTO {

    private LocalDateTime fechaSubida;
    private String nombreOriginal;

    public ImageResponseDTO(LocalDateTime fechaSubida, String nombreOriginal) {
        this.fechaSubida=fechaSubida;
        this.nombreOriginal= nombreOriginal;
    }

    public static ImageResponseDTO desde(ImagenMD image){
        return new ImageResponseDTO(
            image.getFechaSubida(), 
            image.getNombreImagen()
        );
    }

}
