package com.contoso.api.entities;

import org.hibernate.dialect.PostgreSQL94Dialect;
import java.sql.Types;

public class PostGreSQLDialect extends PostgreSQL94Dialect {
    public PostGreSQLDialect() {
        this.registerColumnType(Types.JAVA_OBJECT, "jsonb");
    }
}
