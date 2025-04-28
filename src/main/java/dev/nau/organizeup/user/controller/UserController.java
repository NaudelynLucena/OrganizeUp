package dev.nau.organizeup.user.controller;

import dev.nau.organizeup.user.dto.ChildResponse;
import dev.nau.organizeup.user.model.User;
import dev.nau.organizeup.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/children")
    public ResponseEntity<List<ChildResponse>> getChildren(Authentication authentication) {
        String guardianEmail = authentication.getName();

        List<User> children = userService.getChildrenOfGuardian(guardianEmail);

        List<ChildResponse> response = children.stream()
            .map(child -> new ChildResponse(
                child.getId(),
                child.getName(),
                child.getBirthDate() != null ? child.getBirthDate().toString() : null
            ))
            .toList();

        return ResponseEntity.ok(response);
    }
}
