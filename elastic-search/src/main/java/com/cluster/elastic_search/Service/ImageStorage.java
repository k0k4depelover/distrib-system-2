package com.cluster.elastic_search.Service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageStorage {
    public String guardarImagen(MultipartFile file, String filename);
    public Resource obtener(String filename);
    void eliminar(String filename);

}
