package com.contoso.api.repositories;

import com.contoso.api.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface RoleRepository extends CrudRepository<Role, UUID> {
    Role findRoleByName(String roleName);
}
