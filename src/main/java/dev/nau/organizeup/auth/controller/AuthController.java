package dev.nau.organizeup.auth.controller;

import dev.nau.organizeup.auth.dto.*;
import dev.nau.organizeup.auth.service.AuthService;
import dev.nau.organizeup.user.model.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register/child")
    public ResponseEntity<?> registerChild(@Valid @RequestBody ChildRegisterRequest request,
            Authentication authentication) {
        String guardianEmail = authentication.getName();
        User child = authService.createChildAccount(request, guardianEmail);
        return ResponseEntity.ok("Cuenta para " + child.getName() + " creada exitosamente.");
    }

    @PostMapping("/login/child")
    public ResponseEntity<AuthResponse> loginChild(@RequestBody ChildLoginRequest request) {
        AuthResponse token = authService.loginChild(request.getAccessCode());
        return ResponseEntity.ok(token);
    }

}
