package com.enigma.tokopakedi.repository;

import com.enigma.tokopakedi.constant.ERole;
import com.enigma.tokopakedi.entity.Role;
import com.enigma.tokopakedi.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, String> {

    Optional<UserCredential> findByEmail(String userCredential);
}
