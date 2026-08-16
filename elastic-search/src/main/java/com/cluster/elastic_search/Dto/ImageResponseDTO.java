package com.cluster.elastic_search.Dto;

import java.time.LocalDateTime;

import com.cluster.elastic_search.Model.ImagenMD;


public class ImageResponseDTO {
    private Long id;
    private LocalDateTime fechaSubida;
    private String nombreOriginal;
    private String imageUrl;
    private Boolean confirmed;

    public ImageResponseDTO(Long id, LocalDateTime fechaSubida, 
        String nombreOriginal, String imageUrl, Boolean confirmed) {

        this.fechaSubida=fechaSubida;
        this.nombreOriginal= nombreOriginal;
        this.imageUrl= imageUrl;
        this.confirmed= confirmed;
    }

    public static ImageResponseDTO desde(ImagenMD image){
        return new ImageResponseDTO(
            image.getId(),
            image.getFechaSubida(), 
            image.getNombreOriginal(),
            image.getImageUrl(),
            image.getConfirmed()
        );
    }

}
