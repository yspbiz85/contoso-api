package com.contoso.api.model;

import com.contoso.api.model.Edge;
import com.contoso.api.model.Node;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GraphData {
    private String label;
    private String title;
    private String type;
    private Boolean directed;
    private Integer height;
    private Integer width;
    private List<Node> nodes;
    private List<Edge> edges;
}
