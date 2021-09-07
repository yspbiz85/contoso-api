package com.contoso.api.controllers;

import com.contoso.api.model.GraphDao;
import com.contoso.api.services.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/graph")
public class GraphController {

    @Autowired
    GraphService graphService;

    @PostMapping("/save")
    public ResponseEntity<GraphDao> saveGraph(Authentication authentication,
                                              @RequestBody GraphDao graphDao) {
        return new ResponseEntity<>(this.graphService.saveGraph(authentication,graphDao),
                HttpStatus.CREATED);
    }

    @GetMapping("/{graphId}")
    public ResponseEntity<GraphDao> findGraph(Authentication authentication, @PathVariable("graphId") UUID graphId) {
        return new ResponseEntity<GraphDao>(this.graphService.findGraph(authentication,graphId),HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<GraphDao>> findAllGraph(Authentication authentication) {
        return new ResponseEntity<>(this.graphService.findAllGraph(authentication),HttpStatus.OK);
    }

    @PutMapping("/{graphId}")
    public ResponseEntity<GraphDao> updateGraph(Authentication authentication, @RequestBody GraphDao graphDao) {
        return new ResponseEntity<>(this.graphService.updateGraph(authentication,graphDao),HttpStatus.OK);
    }

    @DeleteMapping("/{graphId}")
    public ResponseEntity<Boolean> deleteGraph(Authentication authentication,@PathVariable("graphId") UUID graphId) {
        return new ResponseEntity<>(this.graphService.deleteGraph(authentication,graphId),HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/all")
    public ResponseEntity<Boolean> deleteAllGraph(Authentication authentication) {
        return new ResponseEntity<>(this.graphService.deleteAllGraph(authentication),HttpStatus.NO_CONTENT);
    }
}
