package dev.nau.organizeup.auth.service;

import dev.nau.organizeup.auth.dto.AuthRequest;
import dev.nau.organizeup.auth.dto.AuthResponse;
import dev.nau.organizeup.auth.dto.ChildRegisterRequest;
import dev.nau.organizeup.auth.dto.RegisterRequest;
import dev.nau.organizeup.exception.EmailAlreadyExistsException;
import dev.nau.organizeup.exception.ForbiddenActionException;
import dev.nau.organizeup.exception.InvalidCredentialsException;
import dev.nau.organizeup.security.jwt.JwtUtils;
import dev.nau.organizeup.user.model.Role;
import dev.nau.organizeup.user.model.User;
import dev.nau.organizeup.user.repository.UserRepository;

import java.util.UUID;

import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtils jwtUtils,
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Este email ya está registrado");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);

        String token = jwtUtils.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponse(token);
    }

    public AuthResponse login(AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (Exception e) {
            throw new InvalidCredentialsException("Credenciales inválidas");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Usuario no encontrado"));

        String token = jwtUtils.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponse(token);
    }

    public User createChildAccount(ChildRegisterRequest request, String guardianEmail) {
        User guardian = userRepository.findByEmail(guardianEmail)
                .orElseThrow(() -> new RuntimeException("Tutor no encontrado"));

        User child = new User();
        child.setName(request.getName());
        child.setBirthDate(request.getBirthDate());
        child.setEmail(UUID.randomUUID().toString() + "@child.local");
        child.setPassword("");
        child.setManagedAccount(true);
        child.setGuardian(guardian);
        child.setRole(Role.CHILD);

        String code = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        child.setAccessCode(code);

        return userRepository.save(child);
    }

    public AuthResponse loginChild(String accessCode) {
        User child = userRepository.findByAccessCode(accessCode)
                .orElseThrow(() -> new InvalidCredentialsException("Código inválido"));

        if (child.getRole() != Role.CHILD) {
            throw new ForbiddenActionException("Este código no pertenece a una cuenta infantil.");
        }

        String token = jwtUtils.generateToken(child.getEmail(), child.getRole().name());
        return new AuthResponse(token);
    }

}