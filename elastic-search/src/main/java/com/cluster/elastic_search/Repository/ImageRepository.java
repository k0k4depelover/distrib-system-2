package com.cluster.elastic_search.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cluster.elastic_search.Model.ImagenMD;

public interface ImageRepository extends JpaRepository<ImagenMD, Long>{
    public List<ImagenMD> findByOwnerId(Long ownerId);
    
}
