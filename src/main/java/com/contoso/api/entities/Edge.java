package com.contoso.api.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Edge implements Serializable {

    @NotNull(message = "Edge source should not be null")
    private String source;

    @NotNull(message = "Edge target should not be null")
    private String target;

    @NotNull(message = "Edge weight should not be null")
    private Integer weight;

    private Boolean directed;

    private String label;

    private Boolean renderLabel;

    private String labelProperty;

    private String color;

    private String fontColor;

    private Integer fontSize;

    private String fontWeight;

    private String mouseCursor;

    private Integer width;

    private Integer opacity;
}
