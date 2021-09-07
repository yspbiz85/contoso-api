package com.contoso.api.model;

import com.contoso.api.entities.GraphData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GraphDao {

    private UUID id;

    private UUID userId;

    @NotEmpty
    @Min(value = 3,message = "Graph name should be minimum 3 character")
    @Min(value = 15,message = "Graph name should be maximum 15 character")
    private String graphName;

    @NotNull(message = "Graph data should not be null")
    private GraphData graphData;
}
