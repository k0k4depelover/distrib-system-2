package com.cluster.elastic_search.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cluster.elastic_search.Service.PrendaService;

@RestController
@RequestMapping("/api/prendas")
public class PrendaController {
    private final PrendaService service;

    public PrendaController(PrendaService service) {
        this.service = service;
    }
    
}
