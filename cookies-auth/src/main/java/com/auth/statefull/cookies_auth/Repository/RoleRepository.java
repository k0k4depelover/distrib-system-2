package com.auth.statefull.cookies_auth.Repository;

import org.springframework.data.repository.CrudRepository;

import com.auth.statefull.cookies_auth.Entity.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
    
}
