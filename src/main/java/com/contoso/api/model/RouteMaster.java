package com.contoso.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteMaster {
    private String source;
    private String destination;
    private Integer totalRoutes;
    private List<Route> routes;
}
