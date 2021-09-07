package com.contoso.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MasterGraphSearch {
    private List<GraphSearch> graphSearches;
}
