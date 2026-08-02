package com.cluster.elastic_search.Dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrendaRequest {

    private String nombre;

    private String tipo;

    private String descripcion;

    private String imageUrl;

    private Double precio;


    public String getImageUrl() {
        return imageUrl;
    }

    public void setId(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}



