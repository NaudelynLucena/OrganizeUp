package dev.nau.organizeup.group.service;

import dev.nau.organizeup.group.model.Group;
import dev.nau.organizeup.group.model.JoinRequest;
import dev.nau.organizeup.group.model.RequestStatus;
import dev.nau.organizeup.group.repository.JoinRequestRepository;
import dev.nau.organizeup.user.model.User;
import dev.nau.organizeup.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JoinRequestService {

    private final JoinRequestRepository joinRequestRepository;
    private final UserRepository userRepository;

    public JoinRequestService(JoinRequestRepository joinRequestRepository, UserRepository userRepository) {
        this.joinRequestRepository = joinRequestRepository;
        this.userRepository = userRepository;
    }

    public JoinRequest createJoinRequest(User child, Group group) {
        joinRequestRepository.findByChildAndGroupAndStatus(child, group, RequestStatus.PENDING)
                .ifPresent(req -> {
                    throw new RuntimeException("Ya tienes una solicitud pendiente para este grupo");
                });

        JoinRequest request = new JoinRequest(child, group);
        return joinRequestRepository.save(request);
    }

    public List<JoinRequest> getRequestsForGuardian(User guardian) {
        List<User> children = userRepository.findByGuardian(guardian);
        if (children.isEmpty()) {
            throw new RuntimeException("No tienes hijos registrados.");
        }

        return joinRequestRepository.findAll().stream()
                .filter(req -> children.contains(req.getChild()) && req.getStatus() == RequestStatus.PENDING)
                .toList();
    }

    public JoinRequest approveRequest(Long requestId) {
        JoinRequest request = joinRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
        request.setStatus(RequestStatus.APPROVED);
        return joinRequestRepository.save(request);
    }

    public JoinRequest rejectRequest(Long requestId) {
        JoinRequest request = joinRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
        request.setStatus(RequestStatus.REJECTED);
        return joinRequestRepository.save(request);
    }

    public List<JoinRequest> getRequestsForChild(User child) {
        return joinRequestRepository.findByChild(child);
    }

}
