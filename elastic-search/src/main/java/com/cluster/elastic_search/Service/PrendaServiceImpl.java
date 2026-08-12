package com.cluster.elastic_search.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import com.cluster.elastic_search.Document.Prenda;
import com.cluster.elastic_search.Dto.PrendaRequest;
import com.cluster.elastic_search.Repository.PrendaRepository;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;


@Service
public class PrendaServiceImpl implements PrendaService{

    public PrendaRepository repository;
    public ElasticsearchOperations elasticsearchOperations;

    @Value("${app.image.storage-path}")
    public String storagePath;

    @Value("${app.images.base-url}")
    public String baseUrl;

    public PrendaServiceImpl(PrendaRepository repository, ElasticsearchOperations elasticsearchOperations){
        this.repository= repository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public Prenda crear(PrendaRequest prenda) {
        Prenda prendaCrear = new Prenda();

        prendaCrear.setId(prenda.getId());
        prendaCrear.setNombre(prenda.getNombre());
        prendaCrear.setPrecio(prenda.getPrecio());
        prendaCrear.setDescripcion(prenda.getDescripcion());
        prendaCrear.setImageUrl(prenda.getImageUrl());

        return repository.save(prendaCrear);


    }

    @Override
    public Optional<Prenda> obtenerPorId(Long id) {
        return repository.findById(id);    
    }

    @Override
    public Boolean editarPrenda(Long id, PrendaRequest prenda) {
        Optional<Prenda> optionalPrenda = repository.findById(id);

        if(optionalPrenda.isEmpty()){
            return false;
        }

        optionalPrenda.get().setNombre(prenda.getNombre());
        optionalPrenda.get().setPrecio(prenda.getPrecio());
        optionalPrenda.get().setDescripcion(prenda.getDescripcion());
        optionalPrenda.get().setImageUrl(prenda.getImageUrl()); 
        return true;
    }

    @Override
    public List<Prenda> obtenerTodas() {
        return (List<Prenda>) repository.findAll();
    }

    @Override
    public List<Prenda> busquedaElastic(String nombre) {
        Query query = Query.of(q -> 
            q.multiMatch(m -> m
                        .fields("nombre", "descripcion", "tipo")
                        .query(nombre)
                        .fuzziness("AUTO")       
            )
        );

        NativeQuery searchQuery = NativeQuery.builder().withQuery(query).build();
        SearchHits<Prenda> hits = elasticsearchOperations.search(searchQuery, Prenda.class);
        
        return hits.getSearchHits().stream().map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    @Override
    public List<Prenda> buscarPorTipo(String tipo) {
        return repository.findByTipo(tipo);    
    }

    @Override
    public Boolean eliminar(Long id) {
        Optional<Prenda> prendaEliminar = repository.findById(id);
        if(prendaEliminar.isPresent()){
            repository.deleteById(id);
            return true;
        }

        return false;
        
    }


    
}
