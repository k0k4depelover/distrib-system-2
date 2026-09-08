package com.cluster.elastic_search.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.cluster.elastic_search.Model.ImagenMD;

import jakarta.transaction.Transactional;

public interface ImageRepository extends JpaRepository<ImagenMD, Long>{
public Optional<ImagenMD> findByIdUsuario(Long idUsuario);
    
    @Query("""
            SELECT i
            FROM ImagenMD i
            WHERE i.id = ?1
            AND i.idUsuario = ?2
            """)
    public Optional<ImagenMD> findByIdAndIdUsuario(Long id, Long idUsuario); 

    @Transactional
    @Modifying
    @Query(
        """
        UPDATE ImagenMD i
        SET i.confirmed=true
        WHERE i.id = ?1 AND i.idUsuario = ?2 AND  i.confirmed=false        
        """)
    int confirmarImagenYExtraerImagen(Long idImagen, Long idUsuario);
    

    public  List<ImagenMD> findAllByIdUsuario(Long idUsuario);

    @Query("""
            SELECT i 
            FROM ImagenMD i
                WHERE i.confirmed=FALSE
                AND i.fechaSubida < ?1
            
            """)
    public List<ImagenMD> findByConfirmedFalseAndFechaSubidaBefore(LocalDateTime limit); 


}
