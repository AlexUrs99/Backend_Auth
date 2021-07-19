package com.example.securitytryingstuff.security;

import com.example.securitytryingstuff.security.dto.SignUpRequest;
import com.example.securitytryingstuff.user.model.ERole;
import com.example.securitytryingstuff.user.model.Role;
import com.example.securitytryingstuff.user.model.User;
import com.example.securitytryingstuff.user.repository.RoleRepository;
import com.example.securitytryingstuff.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void registerUser(SignUpRequest signUpRequest) {
        User userToBeRegistered = User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .build();

        Set<String> rolesAsStrings = signUpRequest.getRoles();
        Set<Role> finalRoles = new HashSet<>();

        if (rolesAsStrings.isEmpty()) {
            Role defaultRole = roleRepository.findByName(ERole.CUSTOMER)
                    .orElseThrow(() -> new EntityNotFoundException("Cannot find CUSTOMER role!"));
            finalRoles.add(defaultRole);
        } else {
            rolesAsStrings.forEach(r -> {
                Role foundRole = roleRepository.findByName(ERole.valueOf(r))
                        .orElseThrow(() -> new EntityNotFoundException("Cannot find " + r + " role!"));
                finalRoles.add(foundRole);
            });
        }

        userToBeRegistered.setRoles(finalRoles);
        userRepository.save(userToBeRegistered);
    }


}
