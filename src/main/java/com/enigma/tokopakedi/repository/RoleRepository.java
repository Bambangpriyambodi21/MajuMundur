package com.enigma.tokopakedi.repository;

import com.enigma.tokopakedi.constant.ERole;
import com.enigma.tokopakedi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByRole(ERole role);
}
