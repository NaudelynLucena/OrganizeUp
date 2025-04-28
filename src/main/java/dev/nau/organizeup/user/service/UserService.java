package dev.nau.organizeup.user.service;

import dev.nau.organizeup.exception.ChildrenNotFoundException;
import dev.nau.organizeup.user.model.User;
import dev.nau.organizeup.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getChildrenOfGuardian(String guardianEmail) {
        User guardian = userRepository.findByEmail(guardianEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<User> children = userRepository.findByGuardian(guardian);

        if (children.isEmpty()) {
            throw new ChildrenNotFoundException("Este usuario no gestiona ninguna otra cuenta actualmente");
        }

        return children;
    }

}
