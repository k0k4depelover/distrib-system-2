package com.cluster.elastic_search.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.cluster.elastic_search.Model.ImagenMD;

import jakarta.transaction.Transactional;

public interface ImageRepository extends JpaRepository<ImagenMD, Long>{
    public Optional<ImagenMD> findByOwnerId(Long ownerId);
    
    @Query("""
            SELECT c
            FROM ImagenMD
            WHERE id = ?1
            AND idUsuario = ?2
            """)
    public Optional<ImagenMD> findByIdAndOwnerId(Long id, Long ownerId); 

    @Transactional
    @Modifying
    @Query(
        """
        UPDATE ImageMD i
        SET i.confirmed=true
        WHERE i.id = ?1 AND i.idUsuario = ?2 AND  i.confirmed=false;        
        """)
    int confirmarImagenYExtraerImagen(Long idImagen, Long idUsuario);
    
    @Query("""
            SELECT c
            FROM ImagenMD
            WHERE idUsuario = ?2
            
            """)
    public  List<ImagenMD> findAllByOwnerId(Long ownerId);

    @Query("""
            SELECT * 
            FROM ImagenMD
                WHERE confirmed=FALSE
                AND fechaSubida < ?1
            
            """)
    public List<ImagenMD> findByConfirmedFalseAndfechaSubidaBefore(LocalDateTime limit); 


}
