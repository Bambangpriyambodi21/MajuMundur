package com.enigma.tokopakedi.service.impl;

import com.enigma.tokopakedi.constant.ERole;
import com.enigma.tokopakedi.controller.User;
import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.entity.Role;
import com.enigma.tokopakedi.entity.UserCredential;
import com.enigma.tokopakedi.model.reponse.RoleResponse;
import com.enigma.tokopakedi.model.request.AuthRequest;
import com.enigma.tokopakedi.model.reponse.UserResponse;
import com.enigma.tokopakedi.repository.UserCredentialRepository;
import com.enigma.tokopakedi.security.JwtUtils;
import com.enigma.tokopakedi.service.AuthService;
import com.enigma.tokopakedi.service.CustomerService;
import com.enigma.tokopakedi.service.RoleService;
import com.enigma.tokopakedi.utils.ValidationUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final CustomerService customerService;
    private final ValidationUtils validationUtils;

    @Override
    public String login(AuthRequest request){
        validationUtils.validate(request);
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserCredential userCredential = (UserCredential) authenticate.getPrincipal();
        return jwtUtils.generateToken(userCredential);
    }



    @PostConstruct
    @Transactional(rollbackFor = Exception.class)
    public void initSuperAdmin() {
        Optional<UserCredential> optionalUserCredential = userCredentialRepository.findByEmail("superadmin@gmail.com");

        if (optionalUserCredential.isPresent()) return;

        Role roleSuperAdmin = roleService.getOrSave(ERole.ROLE_SUPER_ADMIN);
        Role roleAdmin = roleService.getOrSave(ERole.ROLE_MERCHANT);
        Role roleCustomer = roleService.getOrSave(ERole.ROLE_CUSTOMER);

        String hashPassword = passwordEncoder.encode("password");

        UserCredential userCredential = UserCredential.builder()
                .email("superadmin@gmail.com")
                .password(hashPassword)
                .roles(List.of(roleCustomer, roleAdmin, roleSuperAdmin))
                .build();
        userCredentialRepository.saveAndFlush(userCredential);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserResponse register(AuthRequest request) {
        validationUtils.validate(request);
        Role roleCustomer = roleService.getOrSave(ERole.ROLE_CUSTOMER);

        String hashPassword = passwordEncoder.encode(request.getPassword());

        UserCredential userCredential = UserCredential.builder()
                .email(request.getEmail())
                .password(hashPassword)
                .roles(List.of(roleCustomer))
                .build();
        userCredentialRepository.saveAndFlush(userCredential);

        Customer customer = Customer.builder()
                .userCredential(userCredential)
                .build();
        customerService.createCustomer(customer);

        return toUserResponse(userCredential);
    }

    private static UserResponse toUserResponse(UserCredential userCredential){
        List<String> roles = userCredential.getRoles().stream().map(role -> role.getRole().name()).toList();
        return UserResponse.builder()
                .email(userCredential.getEmail())
                .roles(roles)
                .build();

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserResponse registerAdmin(AuthRequest request) {
        validationUtils.validate(request);
        Role roleAdmin = roleService.getOrSave(ERole.ROLE_MERCHANT);
        Role roleCustomer = roleService.getOrSave(ERole.ROLE_CUSTOMER);

        String hashPassword = passwordEncoder.encode(request.getPassword());

        UserCredential userCredential = UserCredential.builder()
                .email(request.getEmail())
                .password(hashPassword)
                .roles(List.of(roleCustomer, roleAdmin))
                .build();
        userCredentialRepository.saveAndFlush(userCredential);

        Customer customer = Customer.builder()
                .userCredential(userCredential)
                .build();
        customerService.createCustomer(customer);

        return toUserResponse(userCredential);
    }
}
