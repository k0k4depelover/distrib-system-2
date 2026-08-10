package com.cluster.elastic_search.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cluster.elastic_search.Document.Prenda;
import com.cluster.elastic_search.Dto.PrendaRequest;

public interface PrendaRepository extends CrudRepository<Prenda, Long>{
     List<Prenda> findByNombreContainingOrDescripcionContaining(String nombre, String descripcion);

     List<Prenda> findByTipo(String tipo);

     Prenda save(PrendaRequest prenda);
}
