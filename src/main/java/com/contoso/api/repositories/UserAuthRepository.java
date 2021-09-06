package com.contoso.api.repositories;

import com.contoso.api.entities.UserAuth;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface UserAuthRepository extends CrudRepository<UserAuth, UUID> {
  UserAuth findUserAuthByUserName(String username);
}
