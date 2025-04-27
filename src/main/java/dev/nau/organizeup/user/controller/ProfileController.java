package dev.nau.organizeup.user.controller;

import dev.nau.organizeup.user.dto.ProfileDto;
import dev.nau.organizeup.user.dto.UpdateProfileRequest;
import dev.nau.organizeup.user.model.User;
import dev.nau.organizeup.user.repository.UserRepository;
import dev.nau.organizeup.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

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
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        ProfileDto profileDto = new ProfileDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getBirthDate(),
                user.getAccumulatedPoints());

        return ResponseEntity.ok(profileDto);
    }

    @PatchMapping("/update")
    public ResponseEntity<ProfileDto> updateProfile(@Valid @RequestBody UpdateProfileRequest request,
            Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        user.setName(request.getName());
        user.setBirthDate(request.getBirthDate());

        User updatedUser = userRepository.save(user);

        ProfileDto profileDto = new ProfileDto(
                updatedUser.getId(),
                updatedUser.getName(),
                updatedUser.getEmail(),
                updatedUser.getBirthDate(),
                updatedUser.getAccumulatedPoints());

        return ResponseEntity.ok(profileDto);
    }
}
