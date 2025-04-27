package dev.nau.organizeup.reward.controller;

import dev.nau.organizeup.reward.dto.CreateRewardRequest;
import dev.nau.organizeup.reward.dto.RewardResponse;
import dev.nau.organizeup.reward.dto.UpdateRewardRequest;
import dev.nau.organizeup.reward.model.Reward;
import dev.nau.organizeup.reward.service.RewardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rewards")
public class RewardController {

    private final RewardService rewardService;

    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @PostMapping("/create")
    public ResponseEntity<RewardResponse> createReward(@Valid @RequestBody CreateRewardRequest request,
            Authentication authentication) {
        String email = authentication.getName();
        Reward reward = rewardService.createReward(request, email);

        RewardResponse response = new RewardResponse(
                reward.getId(),
                reward.getName(),
                reward.getDescription(),
                reward.getPointsCost(),
                reward.getGroup() != null ? reward.getGroup().getName() : null,
                reward.getCreatedBy().getName());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<RewardResponse>> getRewardsByGroup(@PathVariable Long groupId) {
        List<Reward> rewards = rewardService.getRewardsByGroup(groupId);

        List<RewardResponse> response = rewards.stream()
                .map(r -> new RewardResponse(
                        r.getId(),
                        r.getName(),
                        r.getDescription(),
                        r.getPointsCost(),
                        r.getGroup() != null ? r.getGroup().getName() : null,
                        r.getCreatedBy().getName()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/personal")
    public ResponseEntity<List<RewardResponse>> getPersonalRewards(Authentication authentication) {
        String email = authentication.getName();
        List<Reward> rewards = rewardService.getPersonalRewards(email);

        List<RewardResponse> response = rewards.stream()
                .map(r -> new RewardResponse(
                        r.getId(),
                        r.getName(),
                        r.getDescription(),
                        r.getPointsCost(),
                        null,
                        r.getCreatedBy().getName()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{rewardId}/claim")
    public ResponseEntity<String> claimReward(@PathVariable Long rewardId,
            Authentication authentication) {
        String email = authentication.getName();
        rewardService.claimReward(rewardId, email);
        return ResponseEntity.ok("¡Recompensa reclamada exitosamente!");
    }

    @PutMapping("/{rewardId}/edit")
    public ResponseEntity<RewardResponse> editReward(@PathVariable Long rewardId,
            @Valid @RequestBody UpdateRewardRequest request,
            Authentication authentication) {
        String email = authentication.getName();
        Reward reward = rewardService.editReward(rewardId, request, email);

        RewardResponse response = new RewardResponse(
                reward.getId(),
                reward.getName(),
                reward.getDescription(),
                reward.getPointsCost(),
                reward.getGroup() != null ? reward.getGroup().getName() : null,
                reward.getCreatedBy().getName());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{rewardId}")
    public ResponseEntity<String> deleteReward(@PathVariable Long rewardId,
            Authentication authentication) {
        String email = authentication.getName();
        rewardService.deleteReward(rewardId, email);
        return ResponseEntity.ok("Recompensa eliminada exitosamente.");
    }
}
