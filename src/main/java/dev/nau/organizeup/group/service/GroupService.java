package dev.nau.organizeup.group.service;

import dev.nau.organizeup.exception.ForbiddenActionException;
import dev.nau.organizeup.group.model.Group;
import dev.nau.organizeup.user.model.Role;
import dev.nau.organizeup.user.model.User;
import dev.nau.organizeup.group.repository.GroupRepository;
import dev.nau.organizeup.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final JoinRequestService joinRequestService;

    public GroupService(GroupRepository groupRepository, UserRepository userRepository,
            JoinRequestService joinRequestService) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.joinRequestService = joinRequestService;
    }

    public Group createGroup(String groupName, String creatorEmail) {
        User creator = userRepository.findByEmail(creatorEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (creator.getRole() == Role.CHILD) {
            throw new ForbiddenActionException("No puedes crear grupos con una cuenta infantil.");
        }

        String joinCode = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Group group = new Group(groupName, joinCode);
        group.addMember(creator);

        return groupRepository.save(group);
    }

    public Group joinGroup(String joinCode, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Group group = groupRepository.findByJoinCode(joinCode)
                .orElseThrow(() -> new RuntimeException("Código de grupo no válido"));

        if (group.isMember(user)) {
            throw new ForbiddenActionException("Ya eres miembro de este grupo");
        }

        if (user.getRole() == Role.CHILD) {
            joinRequestService.createJoinRequest(user, group);
            throw new ForbiddenActionException("Solicitud enviada para aprobación de tu tutor.");
        }

        if (group.isFull()) {
            throw new ForbiddenActionException("El grupo ya está completo.");
        }

        group.addMember(user);
        return groupRepository.save(group);
    }

    public Group saveGroup(Group group) {
        return groupRepository.save(group);
    }

    public List<Group> getGroupsForUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return groupRepository.findAll().stream()
                .filter(group -> group.isMember(user))
                .toList();
    }
}
