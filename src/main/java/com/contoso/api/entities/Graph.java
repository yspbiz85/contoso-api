package com.contoso.api.entities;

import com.contoso.api.model.Auditable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "graph",schema = "contoso")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
@org.hibernate.annotations.TypeDef(name = "JsonType", typeClass = JsonType.class)
public class Graph extends Auditable<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "graph_id")
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "graph_name")
    private String graphName;

    @Column(name = "graph_data")
    @Type(type = "JsonType")
    private GraphData graphData;
}
