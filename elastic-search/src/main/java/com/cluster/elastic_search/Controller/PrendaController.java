package com.cluster.elastic_search.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cluster.elastic_search.Document.Prenda;
import com.cluster.elastic_search.Dto.ImageResponseDTO;
import com.cluster.elastic_search.Dto.PrendaRequest;
import com.cluster.elastic_search.Service.ImageService;
import com.cluster.elastic_search.Service.PrendaService;
import com.cluster.elastic_search.Service.PrendaServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
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
    private final ImageService imageService;
    public PrendaController(PrendaService prendaService, ImageService imageService) {
        this.prendaService = prendaService;
        this.imageService = imageService;
    }
    
    @GetMapping
    public List<Prenda> obtenerTodos() {
        return prendaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prenda> obtenerPorId(@RequestParam Long id) {
        Optional<Prenda> prendaPorId = prendaService.obtenerPorId(id);
        
        if(prendaPorId.isPresent()){
            ResponseEntity.ok(prendaPorId);
        }

        return ResponseEntity.notFound().build();

    }

    @GetMapping("/{tipo}")
    public ResponseEntity<List<Prenda>> obtenerPorTipo(@RequestParam String tipo) {
        List<Prenda> prendaTipo= prendaService.buscarPorTipo(tipo);

        if (prendaTipo.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(prendaTipo);

    }
    
    
    @PostMapping
    public ResponseEntity<Prenda> crearPrenda(@RequestBody PrendaRequest prenda, @RequestBody MultipartFile file) {
        ImageResponseDTO image = 
        return ResponseEntity.status(202).body(service.crear(prenda));
    }
    
    
    @PutMapping("/{id}")
    public ResponseEntity<?> editarPrenda(@PathVariable Long id, @RequestBody PrendaRequest prenda) {
        boolean prendaEditar= service.editarPrenda(id, prenda);
        
        if(!prendaEditar){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Editado correctamente");
    }
     

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPrenda(@PathVariable Long id){
        Boolean prendaELiminar = service.eliminar(id);
        if(!prendaELiminar){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Eliminado correctamente...");
    }


    @GetMapping("/buscar")
    public ResponseEntity<List<Prenda>> busquedaElastic(@RequestParam String q) {
        return ResponseEntity.ok(service.busquedaElastic(q));
    }
    
    
}
