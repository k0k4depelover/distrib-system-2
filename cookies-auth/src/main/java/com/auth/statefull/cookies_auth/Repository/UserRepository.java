package com.auth.statefull.cookies_auth.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.auth.statefull.cookies_auth.Entity.User;

public interface UserRepository extends CrudRepository<User, Long>{
    Optional<User> findByUsername(String username);
}
