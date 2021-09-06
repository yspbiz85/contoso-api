package com.contoso.api.controllers;

import com.contoso.api.entities.Graph;
import com.contoso.api.model.GraphSaveRequest;
import com.contoso.api.services.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class GraphController {

    @Autowired
    GraphService graphService;

    @PostMapping("/graph/save")
    public ResponseEntity<Graph> saveGraph(Authentication authentication,
                                           @RequestBody GraphSaveRequest graphSaveRequest) {
        return new ResponseEntity<>(this.graphService.saveGraph(authentication,graphSaveRequest),
                HttpStatus.CREATED);
    }

    @GetMapping("/graph/{graphId}")
    public ResponseEntity<Graph> findGraph(Authentication authentication,@PathVariable("graphId") UUID graphId) {
        return new ResponseEntity<>(this.graphService.findGraph(authentication,graphId),HttpStatus.OK);
    }

    @GetMapping("/graph/all")
    public ResponseEntity<List<Graph>> findAllGraph(Authentication authentication) {
        return new ResponseEntity<>(this.graphService.findAllGraph(authentication),HttpStatus.OK);
    }

    @PutMapping("/graph/{graphId}")
    public ResponseEntity<Graph> updateGraph(Authentication authentication, @RequestBody GraphSaveRequest graphSaveRequest) {
        return new ResponseEntity<>(this.graphService.updateGraph(authentication,graphSaveRequest),HttpStatus.OK);
    }

    @DeleteMapping("/graph/{graphId}")
    public ResponseEntity<Boolean> deleteGraph(Authentication authentication,@PathVariable("graphId") UUID graphId) {
        return new ResponseEntity<>(this.graphService.deleteGraph(authentication,graphId),HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/graph/all")
    public ResponseEntity<Boolean> deleteAllGraph(Authentication authentication) {
        return new ResponseEntity<>(this.graphService.deleteAllGraph(authentication),HttpStatus.NO_CONTENT);
    }

}
