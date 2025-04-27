package dev.nau.organizeup.user.controller;

import dev.nau.organizeup.user.dto.ProfileDto;
import dev.nau.organizeup.user.model.User;
import dev.nau.organizeup.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final UserRepository userRepository;

    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<ProfileDto> getProfile(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        ProfileDto profileDto = new ProfileDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getBirthDate(),
                user.getAccumulatedPoints()
        );

        return ResponseEntity.ok(profileDto);
    }
}
