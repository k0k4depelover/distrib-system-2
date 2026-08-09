package com.cluster.elastic_search.Document;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.*;
import lombok.NoArgsConstructor;

@Document(indexName= "prendas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prenda {
    @Id
    private String id;

    @Field(type = FieldType.Text, analyzer = "spanish")
    private String nombre;

    @Field(type = FieldType.Keyword)
    private String tipo;

    @Field(type = FieldType.Text, analyzer = "spanish")
    private String descripcion;

    @Field(type = FieldType.Double)
    private Double precio;

}
