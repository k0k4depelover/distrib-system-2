package com.cluster.elastic_search.Config.Schedulers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cluster.elastic_search.Model.ImagenMD;
import com.cluster.elastic_search.Repository.ImageRepository;
import com.cluster.elastic_search.Service.ImageStorage;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ImageCleaningScheduler {
    private final ImageRepository imageRepository;
    private final ImageStorage imageStorage;
    public ImageCleaningScheduler(ImageRepository imageRepository, ImageStorage imageStorage) {
        this.imageRepository = imageRepository;
        this.imageStorage = imageStorage;
    }

    // Exportado directamente de application.properties.
    @Scheduled(cron = "${scheduler.cleanup.cron}")
    @Transactional
    public void limpiarImagenesHuerfanas(){
        log.info("Iniciando CronJob: Limpieza de imagenes huerfanas...");
        LocalDateTime limit = LocalDateTime.now().minusHours(18);
        List<ImagenMD> huerfanas= imageRepository.findByConfirmedFalseAndfechaSubidaBefore(limit); 
        
        if (huerfanas.isEmpty()){
            log.info("No se encontraron imagenes para eliminar...");
        }
        
        log.info("Se encontraron {} imagenes huerfanas para limpiar ...", huerfanas.size());

        for (ImagenMD image:huerfanas){
            try{
                imageStorage.eliminar(image.getNombreImagen());
                imageRepository.delete(image);
                log.info("Imagen {} purgada con exito!", image.getNombreImagen());

            }catch(Exception e){
                log.error("Error eliminando la imagen con ID: {} ", image.getId(), e);
            }
                    
        }

    }
    
}
