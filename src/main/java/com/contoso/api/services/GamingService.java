package com.contoso.api.services;

import com.contoso.api.model.RouteFilter;
import com.contoso.api.model.RouteMaster;
import java.util.List;
import java.util.UUID;

public interface GamingService {
    List<RouteMaster> findDefaultGraphData();
    RouteMaster findRoutes(UUID graphId,List<String> routes,RouteFilter routeFilter);
}
