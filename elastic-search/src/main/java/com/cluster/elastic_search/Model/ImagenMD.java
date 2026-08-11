package com.cluster.elastic_search.Model;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "images")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ImagenMD {

    @Id
    @Column(name = "id_image")
    private Long id;


    @Column(name="fecha_subida")
    private LocalDateTime fechaSubida;

    @Column(name = "image_url")
    private String imageUrl;
    
    @Column(name = "nombre_original")
    private String nombreOriginal;

    @Column(name = "id_usuario")
    private Long idUsuario;

    public ImagenMD(LocalDateTime fechaSubida, String imageUrl, String nombreOriginal, Long idUsuario) {
        this.fechaSubida = fechaSubida;
        this.imageUrl = imageUrl;
        this.nombreOriginal = nombreOriginal;
        this.idUsuario = idUsuario;
    }

    
}
