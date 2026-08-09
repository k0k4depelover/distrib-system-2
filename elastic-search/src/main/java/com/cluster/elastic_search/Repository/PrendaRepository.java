package com.cluster.elastic_search.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cluster.elastic_search.Document.Prenda;

public interface PrendaRepository extends CrudRepository<Prenda, Long>{
     List<Prenda> findByNombreContainingOrDescripcionContaining(String nombre, String descripcion);

     List<Prenda> findByTipo(String tipo);
}
