package com.backend.stayEasy.config;


import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.backend.stayEasy.dto.SignUpRequest;
import com.backend.stayEasy.enums.Role;
import com.backend.stayEasy.repository.UserRepository;
import com.backend.stayEasy.sevice.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeedDataConfig implements CommandLineRunner {

	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    @Override
    public void run(String... args) throws Exception {
    	if (userRepository.count() == 0) {
    	SignUpRequest admin = SignUpRequest
                      .builder()
                      .firstName("admin")
                      .lastName("admin")
                      .email("admin@gmail.com")
                      .password(passwordEncoder.encode("admin123"))
                      .role(Role.ADMIN)
                      .build();
        
        SignUpRequest user = SignUpRequest
                .builder()
                .firstName("user")
                .lastName("user")
                .email("user@gmail.com")
                .password(passwordEncoder.encode("user123"))
                .role(Role.USER)
                .build();
        
        SignUpRequest owner = SignUpRequest
                .builder()
                .firstName("owner")
                .lastName("owner")
                .email("owner@gmail.com")
                .password(passwordEncoder.encode("owner123"))
                .role(Role.OWNER)
                .build();

        authService.register(user);
        authService.register(owner);
        authService.register(admin);
        log.debug("created ADMIN user - {}", admin);
        log.debug("created OWNER user - {}", owner);
        log.debug("created USER user - {}", user);
    	}
    }

}
