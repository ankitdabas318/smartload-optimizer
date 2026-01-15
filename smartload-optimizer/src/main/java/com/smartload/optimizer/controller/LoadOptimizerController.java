package com.smartload.optimizer.controller;

import com.smartload.optimizer.model.OptimizeRequest;
import com.smartload.optimizer.service.LoadOptimizerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/load-optimizer")
public class LoadOptimizerController {

    private final LoadOptimizerService service;

    public LoadOptimizerController(LoadOptimizerService service) {
        this.service = service;
    }

    @PostMapping("/optimize")
    public ResponseEntity<?> optimize(@RequestBody OptimizeRequest request) {

        if (request == null ||
            request.truck == null ||
            request.orders == null) {
            return ResponseEntity.badRequest().body("Invalid request");
        }

        if (request.orders.size() > 22) {
            return ResponseEntity.status(413).body("Too many orders");
        }

        return ResponseEntity.ok(service.optimize(request));
    }
    
    @GetMapping("/health")
    public String health() {
        return "OK";
    }

}
