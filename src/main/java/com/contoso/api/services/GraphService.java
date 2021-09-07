package com.contoso.api.services;

import com.contoso.api.entities.Graph;
import com.contoso.api.model.GraphDao;
import org.springframework.security.core.Authentication;
import java.util.List;
import java.util.UUID;

public interface GraphService {

    GraphDao saveGraph(Authentication authentication, GraphDao graphDao);

    GraphDao findGraph(Authentication authentication, UUID graphId);

    List<GraphDao> findAllGraph(Authentication authentication);

    GraphDao updateGraph(Authentication authentication, GraphDao graphDao);

    Boolean deleteGraph(Authentication authentication, UUID graphId);

    Boolean deleteAllGraph(Authentication authentication);

}
