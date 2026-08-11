package com.cluster.elastic_search.Service.Helpers;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class helperFuncions {
    /*
        Genera un UUID y lo castea a String.
        Obtiene el nombre original del archivo, despues obtiene el ultimo 
        punto existente en el nombre del archivo para obtener la extension del archivo.
    */
    public String generarNombreUnico(MultipartFile file){
        String originalName=  file.getOriginalFilename();
        String extension ="";
        if (originalName!= null && originalName.contains(".")){
            extension = originalName.substring(originalName.lastIndexOf("."));
        }
        return UUID.randomUUID().toString() + extension;
    }
}
