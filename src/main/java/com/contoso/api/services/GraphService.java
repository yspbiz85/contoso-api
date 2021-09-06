package com.contoso.api.services;

import com.contoso.api.entities.Graph;
import com.contoso.api.model.GraphSaveRequest;
import org.springframework.security.core.Authentication;
import java.util.List;
import java.util.UUID;

public interface GraphService {

    Graph saveGraph(Authentication authentication, GraphSaveRequest graphSaveRequest);

    Graph findGraph(Authentication authentication, UUID graphId);

    List<Graph> findAllGraph(Authentication authentication);

    Graph updateGraph(Authentication authentication, GraphSaveRequest graphSaveRequest);

    Boolean deleteGraph(Authentication authentication, UUID graphId);

    Boolean deleteAllGraph(Authentication authentication);

}
