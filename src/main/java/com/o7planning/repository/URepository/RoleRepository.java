package com.o7planning.repository.URepository;

import com.o7planning.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RoleRepository extends JpaRepository<Role , Long> {

    Role findByName(String name );
}
