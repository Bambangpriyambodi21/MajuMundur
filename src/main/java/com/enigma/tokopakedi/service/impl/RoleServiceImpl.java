package com.enigma.tokopakedi.service.impl;

import com.enigma.tokopakedi.constant.ERole;
import com.enigma.tokopakedi.entity.Role;
import com.enigma.tokopakedi.model.reponse.RoleResponse;
import com.enigma.tokopakedi.repository.RoleRepository;
import com.enigma.tokopakedi.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role getOrSave(ERole eRole) {
        Optional<Role> optionalRole = roleRepository.findByRole(eRole);
        if (optionalRole.isPresent()) return optionalRole.get();

        Role role = Role.builder()
                .role(eRole)
                .build();
        roleRepository.save(role);

//        RoleResponse roleResponse = RoleResponse.builder()
//                .role((List<ERole>) role)
//                .build();
        return role;
    }
}
