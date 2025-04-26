package dev.nau.organizeup.group.service;

import dev.nau.organizeup.group.model.Group;
import dev.nau.organizeup.group.model.JoinRequest;
import dev.nau.organizeup.group.model.RequestStatus;
import dev.nau.organizeup.group.repository.JoinRequestRepository;
import dev.nau.organizeup.user.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JoinRequestService {

    private final JoinRequestRepository joinRequestRepository;

    public JoinRequestService(JoinRequestRepository joinRequestRepository) {
        this.joinRequestRepository = joinRequestRepository;
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
        return List.of();
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
}
