package com.cluster.elastic_search.Service;

import java.util.List;
import java.util.Optional;


import com.cluster.elastic_search.Document.Prenda;
import com.cluster.elastic_search.Dto.PrendaRequest;

public interface IPrendaService {
    public Prenda crear(PrendaRequest prenda);
    public Optional<Prenda> obtenerPorId(Long id);
    public Boolean editarPrenda(Long id, PrendaRequest prenda);
    public List<Prenda> obtenerTodas();
    public List<Prenda> busquedaElastic(String nombre);
    public List<Prenda> buscarPorTipo(String tipo);
    public Boolean eliminar(Long id);

}
