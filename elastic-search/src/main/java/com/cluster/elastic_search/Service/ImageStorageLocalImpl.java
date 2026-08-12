package com.cluster.elastic_search.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cluster.elastic_search.Config.Exceptions.AlmacenamientoException;

@Service
public class ImageStorageLocalImpl implements ImageStorage {

    @Value("${app.image.storage-path}")
    private String storagePath;

    @Value("${app.images.base-url}")
    private String baseUrl;

    @Override
    public String guardarImagen(MultipartFile file, String filename) {
        try{
            Path base = Paths.get(storagePath).toAbsolutePath().normalize();
            Path destino = base.normalize();

            if(!destino.startsWith(base)){
                throw new AlmacenamientoException("Ruta de archivo invalido", null);
            }
        
        Files.createDirectories(base);
        Files.copy(file.getInputStream(), destino , StandardCopyOption.REPLACE_EXISTING);        
        return baseUrl + "/" + filename;
        }
        catch(IOException e){
            throw new AlmacenamientoException("No se puede guardar el archivo", e);
        }


    }
    

    @Override
    public Resource obtener(String filename) {
        try{
            Path archivo = Paths.get(storagePath).resolve(filename).normalize();
            Resource resource = new UrlResource(archivo.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else{
                throw new AlmacenamientoException("No se encontro el archivo", null);
            }

        } catch(MalformedURLException e){
            throw new AlmacenamientoException("No se encontro el archivo", e);
        }

    }

    @Override
    public void eliminar(String filename) {
        try{
            Files.deleteIfExists(Paths.get(storagePath).resolve(filename).normalize());
        }
        catch(IOException e){
            throw new AlmacenamientoException("No se pudo eliminar el archivo ", e);
        }
    }

}
