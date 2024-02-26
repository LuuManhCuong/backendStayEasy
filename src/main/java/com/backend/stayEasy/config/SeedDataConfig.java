package com.backend.stayEasy.config;


import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.backend.stayEasy.dto.SignUpRequest;
import com.backend.stayEasy.enums.Role;
import com.backend.stayEasy.repository.UserRepository;
import com.backend.stayEasy.sevice.AuthService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SeedDataConfig implements CommandLineRunner {

	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    @Override
    public void run(String... args) throws Exception {
        
    	if (userRepository.count() == 0) {
        SignUpRequest user = SignUpRequest
                .builder()
                .email("user@gmail.com")
                .password(passwordEncoder.encode("user123"))
                .firstName("Minh")
                .lastName("Tran Cong")
                .role(Role.USER)
                .build();
        authService.register(user);
        System.out.println("created USER user - {}"+user);
    	}
    }

}
