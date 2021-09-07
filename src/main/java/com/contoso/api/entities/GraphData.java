package com.contoso.api.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GraphData implements Serializable {

    private String label;

    private String title;

    private String type;

    private Boolean directed;

    private Integer height;

    private Integer width;

    @NotNull(message = "Graph should have at least one node ")
    private List<Node> nodes;

    @NotNull(message = "Graph should have at least one edge ")
    private List<Edge> edges;

}
