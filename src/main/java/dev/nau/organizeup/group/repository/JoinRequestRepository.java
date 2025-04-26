package dev.nau.organizeup.group.repository;

import dev.nau.organizeup.group.model.JoinRequest;
import dev.nau.organizeup.user.model.User;
import dev.nau.organizeup.group.model.Group;
import dev.nau.organizeup.group.model.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {

    List<JoinRequest> findByChildAndStatus(User child, RequestStatus status);
    List<JoinRequest> findByGroupAndStatus(Group group, RequestStatus status);
    List<JoinRequest> findByChild(User child);
    Optional<JoinRequest> findByChildAndGroupAndStatus(User child, Group group, RequestStatus status);
}
