package com.cluster.elastic_search.Dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrendaRequest {
    private Long id;

    private String nombre;

    private String tipo;

    private String descripcion;

    private Double precio;

    private Long idImage; // Cambio principal, por request se manda el Id de la foto, que la gestiona el cliente Frontend



}



