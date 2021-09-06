package com.contoso.api.repositories;

import com.contoso.api.entities.Graph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface GraphRepository extends CrudRepository<Graph, UUID> {

    Graph findGraphByIdAndUserId(UUID graphId,UUID userId);

    List<Graph> findGraphByUserId(UUID userId);



}
