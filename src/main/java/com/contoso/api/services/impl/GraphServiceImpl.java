package com.contoso.api.services.impl;

import com.contoso.api.entities.Graph;
import com.contoso.api.model.GraphSaveRequest;
import com.contoso.api.model.TokenDetails;
import com.contoso.api.repositories.GraphRepository;
import com.contoso.api.services.GraphService;
import com.contoso.api.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class GraphServiceImpl implements GraphService {

    @Autowired
    GraphRepository graphRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    public Graph saveGraph(Authentication authentication, GraphSaveRequest graphSaveRequest) {
        TokenDetails tokenDetails = this.jwtTokenUtil.parseJwtToken(authentication);
        Graph graph = new Graph();
        graph.setUserId(tokenDetails.getUserId());
        graph.setGraphName(graphSaveRequest.getGraphName());
        graph.setGraphData(graphSaveRequest.getGraphData());
        return this.graphRepository.save(graph);
    }

    @Override
    public Graph findGraph(Authentication authentication, UUID graphId) {
        TokenDetails tokenDetails = this.jwtTokenUtil.parseJwtToken(authentication);
        Graph graph = this.graphRepository.findGraphByIdAndUserId(graphId,tokenDetails.getUserId());
        return graph;
    }

    @Override
    public List<Graph> findAllGraph(Authentication authentication) {
        TokenDetails tokenDetails = this.jwtTokenUtil.parseJwtToken(authentication);
        List<Graph> graphs = this.graphRepository.findGraphByUserId(tokenDetails.getUserId());
        return graphs;
    }

    @Override
    public Graph updateGraph(Authentication authentication, GraphSaveRequest graphSaveRequest) {
        TokenDetails tokenDetails = this.jwtTokenUtil.parseJwtToken(authentication);
        Graph graph = this.graphRepository.findGraphByIdAndUserId(graphSaveRequest.getId(),tokenDetails.getUserId());
        graph.setGraphName(graphSaveRequest.getGraphName());
        graph.setGraphData(graphSaveRequest.getGraphData());
        return this.graphRepository.save(graph);
    }

    @Override
    public Boolean deleteGraph(Authentication authentication, UUID graphId) {
        TokenDetails tokenDetails = this.jwtTokenUtil.parseJwtToken(authentication);
        Graph graph = this.graphRepository.findGraphByIdAndUserId(graphId,tokenDetails.getUserId());
        this.graphRepository.delete(graph);
        return true;
    }

    @Override
    public Boolean deleteAllGraph(Authentication authentication) {
        TokenDetails tokenDetails = this.jwtTokenUtil.parseJwtToken(authentication);
        List<Graph> graphs = this.graphRepository.findGraphByUserId(tokenDetails.getUserId());
        this.graphRepository.deleteAll(graphs);
        return true;
    }
}
