package com.contoso.api.controllers;


import com.contoso.api.model.RouteMaster;
import com.contoso.api.services.GamingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/gaming")
public class GamingController {

    @Autowired
    GamingService gamingService;

    @GetMapping("/default")
    public ResponseEntity<List<RouteMaster>> findDefaultGraph() {
        return new ResponseEntity<>(this.gamingService.findDefaultGraphData(), HttpStatus.OK);
    }
}
