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
import com.cluster.elastic_search.Model.ImagenMD;
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
    // Inyeccion de dependencias de ImageService, para corroborar la existencia de imagenes.

    private final ImageService imageService;

    public PrendaServiceImpl(PrendaRepository repository, ElasticsearchOperations elasticsearchOperations, ImageService imageService){
        this.imageService = imageService;
        this.repository= repository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public Prenda crear(PrendaRequest prenda, Long usuarioId) {

        ImagenMD imageConfirm = imageService.confirmarYExtraer(prenda.getIdImage(), usuarioId);

        Prenda prendaCrear = new Prenda();

        prendaCrear.setId(prenda.getId());
        prendaCrear.setNombre(prenda.getNombre());
        prendaCrear.setPrecio(prenda.getPrecio());
        prendaCrear.setDescripcion(prenda.getDescripcion());
        prendaCrear.setImageUrl(imageConfirm.getImageUrl());
        prendaCrear.setUsuarioId(usuarioId);
        return repository.save(prendaCrear);


    }

    @Override
    public Optional<Prenda> obtenerPorId(Long id) {
        return repository.findById(id);    
    }

    @Override
        public Boolean editarPrenda(Long id, Long usuarioId, PrendaRequest prendaDto) {
            Optional<Prenda> optionalPrenda = repository.findByIdAndUsuarioId(id, usuarioId);

            if (optionalPrenda.isEmpty()) {
                return false; 
            }

            Prenda prendaExistente = optionalPrenda.get();
            prendaExistente.setNombre(prendaDto.getNombre());
            prendaExistente.setPrecio(prendaDto.getPrecio());
            prendaExistente.setDescripcion(prendaDto.getDescripcion());
            prendaExistente.setTipo(prendaDto.getTipo());

            if (prendaDto.getIdImage() != null) {
                ImagenMD nuevaImagen = imageService.confirmarYExtraer(prendaDto.getIdImage(), usuarioId);
                prendaExistente.setImageUrl(nuevaImagen.getImageUrl());
            }

            repository.save(prendaExistente);
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
        public Boolean eliminar(Long id, Long usuarioId) {
            // 1. Verificar existencia y pertenencia antes de borrar
            Optional<Prenda> prendaEliminar = repository.findByIdAndUsuarioId(id, usuarioId);

            if (prendaEliminar.isEmpty()) {
                return false;
            }

            // 2. Eliminar del índice
            repository.deleteById(id);
            return true;
        }


    
}
