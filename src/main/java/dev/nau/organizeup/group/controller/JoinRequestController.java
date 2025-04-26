package dev.nau.organizeup.group.controller;

import dev.nau.organizeup.group.dto.JoinRequestResponse;
import dev.nau.organizeup.group.model.Group;
import dev.nau.organizeup.group.model.JoinRequest;
import dev.nau.organizeup.group.model.RequestStatus;
import dev.nau.organizeup.group.service.GroupService;
import dev.nau.organizeup.group.service.JoinRequestService;
import dev.nau.organizeup.user.model.User;
import dev.nau.organizeup.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/join-requests")
public class JoinRequestController {

    private final JoinRequestService joinRequestService;
    private final UserRepository userRepository;
    private final GroupService groupService;

    public JoinRequestController(JoinRequestService joinRequestService, UserRepository userRepository,
            GroupService groupService) {
        this.joinRequestService = joinRequestService;
        this.userRepository = userRepository;
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<List<JoinRequestResponse>> getPendingRequests(Authentication authentication) {
        String guardianEmail = authentication.getName();
        User guardian = userRepository.findByEmail(guardianEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<JoinRequest> requests = joinRequestService.getRequestsForGuardian(guardian);

        List<JoinRequestResponse> response = requests.stream()
                .map(req -> new JoinRequestResponse(
                        req.getId(),
                        req.getChild().getName(),
                        req.getGroup().getName(),
                        req.getStatus().name()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<String> approveRequest(@PathVariable Long id) {
        JoinRequest request = joinRequestService.approveRequest(id);

        if (request.getStatus() == RequestStatus.APPROVED) {
            Group group = request.getGroup();
            User child = request.getChild();
            group.addMember(child);
            groupService.saveGroup(group);
        }

        return ResponseEntity.ok("Solicitud aprobada y niño agregado al grupo.");
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<String> rejectRequest(@PathVariable Long id) {
        joinRequestService.rejectRequest(id);
        return ResponseEntity.ok("Solicitud rechazada.");
    }

    @GetMapping("/mine")
    public ResponseEntity<List<JoinRequestResponse>> getMyRequests(Authentication authentication) {
        String childEmail = authentication.getName();
        User child = userRepository.findByEmail(childEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<JoinRequest> requests = joinRequestService.getRequestsForChild(child);

        List<JoinRequestResponse> response = requests.stream()
                .map(req -> new JoinRequestResponse(
                        req.getId(),
                        req.getChild().getName(),
                        req.getGroup().getName(),
                        req.getStatus().name()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
