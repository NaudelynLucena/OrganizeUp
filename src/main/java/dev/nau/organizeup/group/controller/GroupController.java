package dev.nau.organizeup.group.controller;

import dev.nau.organizeup.group.dto.CreateGroupRequest;
import dev.nau.organizeup.group.dto.GroupResponse;
import dev.nau.organizeup.group.dto.JoinGroupRequest;
import dev.nau.organizeup.group.model.Group;
import dev.nau.organizeup.group.service.GroupService;
import dev.nau.organizeup.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService, UserRepository userRepository) {
        this.groupService = groupService;
    }

    @PostMapping("/create")
    public ResponseEntity<GroupResponse> createGroup(@Valid @RequestBody CreateGroupRequest request,
            Authentication authentication) {
        String email = authentication.getName();
        Group group = groupService.createGroup(request.getName(), email);
        GroupResponse response = new GroupResponse(group.getId(), group.getName(), group.getJoinCode());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/join")
    public ResponseEntity<GroupResponse> joinGroup(@Valid @RequestBody JoinGroupRequest request,
            Authentication authentication) {
        String email = authentication.getName();
        Group group = groupService.joinGroup(request.getJoinCode(), email);
        GroupResponse response = new GroupResponse(group.getId(), group.getName(), group.getJoinCode());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<GroupResponse>> getMyGroups(Authentication authentication) {
        List<Group> groups = groupService.getGroupsForUser(authentication.getName());
        List<GroupResponse> response = groups.stream()
                .map(group -> new GroupResponse(group.getId(), group.getName(), group.getJoinCode()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}