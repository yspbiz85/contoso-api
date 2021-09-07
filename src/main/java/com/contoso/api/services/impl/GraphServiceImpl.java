package com.contoso.api.services.impl;

import com.contoso.api.entities.Graph;
import com.contoso.api.entities.Node;
import com.contoso.api.exceptions.GraphException;
import com.contoso.api.model.GraphDao;
import com.contoso.api.model.TokenDetails;
import com.contoso.api.repositories.GraphRepository;
import com.contoso.api.services.GraphService;
import com.contoso.api.utils.JwtTokenUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GraphServiceImpl implements GraphService {

    private static final Logger logger = LogManager.getLogger(GraphServiceImpl.class);

    @Autowired
    GraphRepository graphRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    public GraphDao saveGraph(Authentication authentication, GraphDao graphDao) {

        if(!isGraphEdgesHasValidNode(graphDao)) {
            throw new GraphException("One or more edge (source/target) nodes, " +
                    "does not matches with nodes from node array");
        }

        TokenDetails tokenDetails = this.jwtTokenUtil.parseJwtToken(authentication);
        Graph graph = new Graph();
        graph.setUserId(tokenDetails.getUserId());
        graph.setGraphName(graphDao.getGraphName());
        graph.setGraphData(graphDao.getGraphData());
        graph = this.graphRepository.save(graph);
        return GraphDao.builder()
                .id(graph.getId())
                .userId(graph.getUserId())
                .graphName(graph.getGraphName())
                .graphData(graph.getGraphData())
                .build();
    }

    @Override
    public GraphDao findGraph(Authentication authentication, UUID graphId) {
        TokenDetails tokenDetails = this.jwtTokenUtil.parseJwtToken(authentication);
        Graph graph = this.graphRepository.findGraphByIdAndUserId(graphId,tokenDetails.getUserId())
                .orElseThrow(() -> new GraphException("Exception occurred while retrieving graph data"));
        return GraphDao.builder()
                .id(graph.getId())
                .userId(graph.getUserId())
                .graphName(graph.getGraphName())
                .graphData(graph.getGraphData())
                .build();
    }

    @Override
    public List<GraphDao> findAllGraph(Authentication authentication) {
        TokenDetails tokenDetails = this.jwtTokenUtil.parseJwtToken(authentication);
        List<Graph> graphs = this.graphRepository.findGraphByUserId(tokenDetails.getUserId())
                .orElseThrow(() -> new GraphException("Exception occurred while retrieving all graph data"));

        List<GraphDao> graphDaoList = new ArrayList<>();
        graphs.stream().forEach(graph -> {
            graphDaoList.add(
                    GraphDao.builder()
                    .id(graph.getId())
                    .userId(graph.getUserId())
                    .graphName(graph.getGraphName())
                    .graphData(graph.getGraphData())
                    .build()
            );
        });
        return graphDaoList;
    }

    @Override
    public GraphDao updateGraph(Authentication authentication, GraphDao graphDao) {
        TokenDetails tokenDetails = this.jwtTokenUtil.parseJwtToken(authentication);
        Graph graph = this.graphRepository.findGraphByIdAndUserId(graphDao.getId(),tokenDetails.getUserId())
                .orElseThrow(() -> new GraphException("Exception occurred while retrieving graph data"));
        graph.setGraphName(graphDao.getGraphName());
        graph.setGraphData(graphDao.getGraphData());
        graph = this.graphRepository.save(graph);
        return GraphDao.builder()
                .id(graph.getId())
                .userId(graph.getUserId())
                .graphName(graph.getGraphName())
                .graphData(graph.getGraphData())
                .build();
    }

    @Override
    public Boolean deleteGraph(Authentication authentication, UUID graphId) {
        TokenDetails tokenDetails = this.jwtTokenUtil.parseJwtToken(authentication);
        Graph graph = this.graphRepository.findGraphByIdAndUserId(graphId,tokenDetails.getUserId())
                .orElseThrow(() -> new GraphException("Exception occurred while deleting graph data"));
        this.graphRepository.delete(graph);
        return true;
    }

    @Override
    public Boolean deleteAllGraph(Authentication authentication) {
        TokenDetails tokenDetails = this.jwtTokenUtil.parseJwtToken(authentication);
        List<Graph> graphs = this.graphRepository.findGraphByUserId(tokenDetails.getUserId())
                .orElseThrow(() -> new GraphException("Exception occurred while deleting all graph data"));
        this.graphRepository.deleteAll(graphs);
        return true;
    }

    private Boolean isGraphEdgesHasValidNode(GraphDao graphDao){
        //Collect all the nodes define for the graph to single set
        TreeSet<String> baseNodes = graphDao.getGraphData()
                                    .getNodes()
                                    .stream()
                                    .map(Node::getLabel)
                                    .collect(Collectors.toCollection(TreeSet::new));

        //Collect all the unique source and target node to single set
        TreeSet<String> edgeNodes = graphDao.getGraphData()
                                    .getEdges()
                                    .stream()
                                    .flatMap(edge -> Stream.of(edge.getSource(),edge.getTarget()))
                                    .collect(Collectors.toCollection(TreeSet::new));
        //Check all the edge node is valid node from based node
        return CollectionUtils.isEqualCollection(baseNodes,edgeNodes);
    }
}
