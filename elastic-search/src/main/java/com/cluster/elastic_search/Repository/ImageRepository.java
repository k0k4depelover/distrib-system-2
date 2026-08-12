package com.cluster.elastic_search.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cluster.elastic_search.Model.ImagenMD;

public interface ImageRepository extends JpaRepository<ImagenMD, Long>{
    public List<ImagenMD> findByOwnerId(Long ownerId);
    
    @Query("""
            SELECT c
            FROM ImagenMD
            WHERE id = ?1
            AND idUsuario = ?2
            """)
    public Optional<ImagenMD> findByIdAndOwnerId(Long id, Long ownerId); 
}
