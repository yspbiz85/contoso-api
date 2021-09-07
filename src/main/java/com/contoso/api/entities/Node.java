package com.contoso.api.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Node implements Serializable {

    private String label;

    private Boolean renderLabel;

    private String tooltip;

    private Boolean renderTooltip;

    private String symbolType;

    private Integer opacity;

    private String color;

    private String fontColor;

    private Integer fontSize;

    private String fontWeight;

    private String labelProperty;

    private String mouseCursor;

    private Integer size;
}
