package com.cluster.elastic_search.Service;

import java.util.List;
import java.util.Optional;


import com.cluster.elastic_search.Document.Prenda;
import com.cluster.elastic_search.Dto.PrendaRequest;

public interface PrendaService {
    public Prenda crear(PrendaRequest prenda, Long usuarioId);
    public Optional<Prenda> obtenerPorId(Long id);
    public Boolean editarPrenda(Long id, Long usuarioId, PrendaRequest prenda);
    public List<Prenda> obtenerTodas();
    public List<Prenda> busquedaElastic(String nombre);
    public List<Prenda> buscarPorTipo(String tipo);
    public Boolean eliminar(Long id, Long usuarioId);

}
