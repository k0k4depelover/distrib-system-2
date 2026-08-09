package com.cluster.elastic_search.Dto;

import java.time.LocalDateTime;

import com.cluster.elastic_search.Model.ImagenMD;


public class ImageResponseDTO {
    Long id;
    private LocalDateTime fechaSubida;
    private String imageUrl;
    private String nombreOriginal;

    public ImageResponseDTO(Long id, LocalDateTime fechaSubida, String imageUrl, String nombreOriginal) {
        this.id=id;
        this.fechaSubida=fechaSubida;
        this.imageUrl= imageUrl;
        this.nombreOriginal= nombreOriginal;
    }

    private static ImageResponseDTO desde(ImagenMD image){
        return new ImageResponseDTO(
            image.getId(), 
            image.getFechaSubida(), 
            image.getImageUrl(), 
            image.getNombreOriginal());
    }

}
