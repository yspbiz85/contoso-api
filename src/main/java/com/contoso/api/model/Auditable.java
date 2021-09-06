package com.contoso.api.model;

import java.util.Date;
import javax.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
public abstract class Auditable<U> {
	
	@CreatedBy
	@Column(name = "created_by")
    protected U createdBy;

	@LastModifiedBy
	@Column(name = "last_modified_by")
	protected U lastModifiedBy;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
    protected Date createdDate;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_modified_date")
    protected Date lastModifiedDate;

}
