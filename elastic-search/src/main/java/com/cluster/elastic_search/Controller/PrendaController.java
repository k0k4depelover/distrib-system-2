package com.cluster.elastic_search.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cluster.elastic_search.Document.Prenda;
import com.cluster.elastic_search.Dto.PrendaRequest;
import com.cluster.elastic_search.Service.PrendaService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;





@RestController
@RequestMapping("/api/prendas")
public class PrendaController {
    private final PrendaService prendaService;
    public PrendaController(PrendaService prendaService) {
        this.prendaService = prendaService;
    }
    
    @GetMapping
    public ResponseEntity<List<Prenda>> obtenerTodos() {
        List<Prenda> listaPrendas = prendaService.obtenerTodas();
        return ResponseEntity.ok(listaPrendas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prenda> obtenerPorId(@PathVariable Long id) {
        Optional<Prenda> prendaPorId = prendaService.obtenerPorId(id);
        
        if(prendaPorId.isPresent()){
            return ResponseEntity.ok().body(prendaPorId.get());
        }

        return ResponseEntity.notFound().build();

    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Prenda>> obtenerPorTipo(@RequestParam String tipo) {
        List<Prenda> prendaTipo= prendaService.buscarPorTipo(tipo);

        if (prendaTipo.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(prendaTipo);

    }
    
    
    @PostMapping
    public ResponseEntity<Prenda> crearPrenda(@RequestBody PrendaRequest prenda, Authentication authentication) {
        Long usuarioId = (Long) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED).body(prendaService.crear(prenda, usuarioId));
    }
    
    
    @PutMapping("/{id}")
    public ResponseEntity<?> editarPrenda(@PathVariable Long id, @RequestBody PrendaRequest prenda, Authentication authentication) {
        Long usuarioId = (Long) authentication.getPrincipal();
        boolean prendaEditar= prendaService.editarPrenda(id, usuarioId, prenda);
        
        if(!prendaEditar){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Editado correctamente");
    }
     

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPrenda(@PathVariable Long id, Authentication authentication){
        Long usuarioId = (Long) authentication.getPrincipal();
        Boolean prendaELiminar = prendaService.eliminar(id, usuarioId);
        if(!prendaELiminar){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Eliminado correctamente...");
    }


    @GetMapping("/buscar")
    public ResponseEntity<List<Prenda>> busquedaElastic(@RequestParam String q) {
        return ResponseEntity.ok(prendaService.busquedaElastic(q));
    }
    
    
}
