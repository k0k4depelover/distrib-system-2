package com.cluster.elastic_search.Dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrendaRequest {
    private Long id;

    private String nombreImagen;

    private String tipo;

    private String descripcion;

    private String imageUrl;

    private Double precio;



}



