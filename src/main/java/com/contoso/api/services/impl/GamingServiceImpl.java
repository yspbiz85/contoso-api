package com.contoso.api.services.impl;

import com.contoso.api.entities.Edge;
import com.contoso.api.entities.Graph;
import com.contoso.api.exceptions.ResourceNotFoundException;
import com.contoso.api.model.*;
import com.contoso.api.repositories.GraphRepository;
import com.contoso.api.services.GamingService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GamingServiceImpl implements GamingService {

    @Autowired
    GraphRepository graphRepository;

    @Value("${app.default.graph.id}")
    UUID defaultGraphId;

    @Override
    public List<RouteMaster> findDefaultGraphData() {
        List<RouteMaster> routeMasters = new ArrayList<>();

        RouteMaster routeMaster = this.findRoutes(defaultGraphId,Arrays.asList("A","B","C"),null);
        routeMasters.add(routeMaster);

        routeMaster = this.findRoutes(defaultGraphId,Arrays.asList("A","E","B","C","D"),null);
        routeMasters.add(routeMaster);

        routeMaster = this.findRoutes(defaultGraphId,Arrays.asList("A","E","D"),null);
        routeMasters.add(routeMaster);

        RouteFilter routeFilter = new RouteFilter("STOPS",RouteFilterConditions.MAX.name(), 2);
        routeMaster = this.findRoutes(defaultGraphId,Arrays.asList("A","C"),routeFilter);
        routeMasters.add(routeMaster);

        return routeMasters;
    }

    @Override
    public List<RouteMaster> findGraphData(MasterGraphSearch masterGraphSearch) {
        List<RouteMaster> routeMasters = new ArrayList<>();
        masterGraphSearch.getGraphSearches().stream().forEach(graphSearch -> {
            routeMasters.add(this.findRoutes(graphSearch.getGraphId(),
                    graphSearch.getRoutes(),null));
        });
        return routeMasters;
    }

    @Override
    public RouteMaster findRoutes(UUID graphId,List<String> routesToSearch,RouteFilter routeFilter) {
        routesToSearch = routesToSearch.stream().distinct().collect(Collectors.toList());
        Graph graph = this.graphRepository.findById(graphId)
                .orElseThrow(()-> new ResourceNotFoundException("Error occurred while retrieving graph data for graph id :  "+graphId));
        RouteMaster routeMaster = new RouteMaster();
        routeMaster.setSource(routesToSearch.get(0));
        routeMaster.setDestination(routesToSearch.get(routesToSearch.size()-1));
        List<Route> allRoutes = this.findAllPossibleRoutes(graph.getGraphData().getEdges(),routesToSearch.get(0),routesToSearch.get(routesToSearch.size()-1));
        if(CollectionUtils.isNotEmpty(routesToSearch) && routesToSearch.size() > 2) {
            allRoutes = filterOutRouteOnBasisOfStops(allRoutes,routesToSearch);
        }
        if(ObjectUtils.isNotEmpty(routeFilter)) {
            allRoutes = filterOutRouteBasedOnCondition(allRoutes,routeFilter);
        }
        routeMaster.setTotalRoutes(allRoutes.size());
        routeMaster.setRoutes(allRoutes);
        return routeMaster;
    }

    private List<Route> findAllPossibleRoutes(List<Edge> edge, String source, String destination) {
            Map<String, List<Edge>> graph = edge.parallelStream()
                .collect(Collectors.groupingBy(Edge::getSource));
        List<String> beingVisited = new ArrayList<>();
        List<String> route = new ArrayList<>();
        List<Route> routes = new ArrayList<>();
        int totalDistance = 0;
        route.add(source);
        search(graph, source, destination, route, beingVisited, routes,totalDistance,source);
        return routes;
    }

    private void search(Map<String, List<Edge>> graph, String source, String destination, List<String> route,
                       List<String> beingVisited, List<Route> paths,int totalDistance,String root) {
        beingVisited.add(source);
        if (source.equalsIgnoreCase(destination)) {
            String listString = route.stream().map(Object::toString).collect(Collectors.joining("->"));
            Route currentPath = new Route();
            currentPath.setRoute(listString);
            currentPath.setDistance(totalDistance);
            currentPath.setLabel("The distance between landmarks via the route "+ listString +" is "+ totalDistance + " km.");
            paths.add(currentPath);
        } else {
            for (Edge adjnode : graph.get(source)) {
                if (!beingVisited.contains(adjnode.getTarget())) {
                    route.add(adjnode.getTarget());
                    if(source.equals(root)) {
                        totalDistance = 0;//To Reset the distance
                    }
                    totalDistance = totalDistance + adjnode.getWeight();
                    search(graph, adjnode.getTarget(), destination, route, beingVisited, paths,totalDistance,root);
                    route.remove(adjnode.getTarget());
                    totalDistance = totalDistance - adjnode.getWeight();//Backtrack the distance
                }
            }
        }
        beingVisited.remove(source);
    }

    private List<Route> filterOutRouteOnBasisOfStops(List<Route> allRoutes,List<String> routesToSearch) {
        String routeToSearch = routesToSearch.stream().collect(Collectors.joining("->"));
        allRoutes = allRoutes.stream()
                .filter(route -> route.getRoute().equals(routeToSearch))
                .collect(Collectors.toList());
        if(CollectionUtils.isEmpty(allRoutes)) {
            Route route = new Route();
            route.setRoute(routeToSearch);
            route.setDistance(0);
            route.setLabel("Path not found.");
            allRoutes.clear();
            allRoutes.add(route);
        }
        return allRoutes;
    }

    private List<Route> filterOutRouteBasedOnCondition(List<Route> allRoutes,RouteFilter routeFilter) {

        if(routeFilter.getCondition().equals(RouteFilterConditions.MAX.name()) && routeFilter.getFilterTarget().equals("STOPS")){
            allRoutes = allRoutes.stream()
                    .filter(route -> route.getRoute().split("->").length < routeFilter.getConditionValue() + 3)
                    .collect(Collectors.toList());
        }

        if(routeFilter.getCondition().equals(RouteFilterConditions.MIN.name()) && routeFilter.getFilterTarget().equals("STOPS")){
            allRoutes = allRoutes.stream()
                    .filter(route -> route.getRoute().split("->").length > routeFilter.getConditionValue() + 3)
                    .collect(Collectors.toList());
        }

        return allRoutes;
    }
}
