package com.cluster.elastic_search.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cluster.elastic_search.Config.Exceptions.AlmacenamientoException;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
@RequiredArgsConstructor 
public class ImageStorageS3Impl implements ImageStorage {

    private final S3Client s3Client;

    @Value("${s3.bucket}")
    public String bucket;

    @Override
    public String guardarImagen(MultipartFile file, String filename) {
        try{
        s3Client.putObject(
            PutObjectRequest.builder()
                .bucket(bucket)
                .key(filename)
                .contentType(file.getContentType())
                .build(),
                
                RequestBody.fromInputStream(file.getInputStream(), file.getSize())
            );

            return filename;            
        }
        catch(IOException e){ 
            throw new AlmacenamientoException("No se encontro el archivo", e);
        }


    }
    

    @Override
    public Resource obtener(String filename) {
        try {
            ResponseInputStream<GetObjectResponse> objectStream = s3Client.getObject(
                GetObjectRequest.builder()
                    .bucket(bucket)
                    .key(filename)
                    .build()
            );
            return new InputStreamResource(objectStream);
        } catch (S3Exception e) {
            throw new AlmacenamientoException("No se encontró el archivo", e);
        }
    }

    @Override
    public void eliminar(String filename) {
        
        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(filename)
                .build()); 

    }

}
