package dev.nau.organizeup.reward.service;

import dev.nau.organizeup.reward.dto.CreateRewardRequest;
import dev.nau.organizeup.reward.dto.UpdateRewardRequest;
import dev.nau.organizeup.reward.model.Reward;
import dev.nau.organizeup.reward.repository.RewardRepository;
import dev.nau.organizeup.group.model.Group;
import dev.nau.organizeup.group.repository.GroupRepository;
import dev.nau.organizeup.user.model.User;
import dev.nau.organizeup.user.repository.UserRepository;
import dev.nau.organizeup.exception.ForbiddenActionException;
import dev.nau.organizeup.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RewardService {

    private final RewardRepository rewardRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public RewardService(RewardRepository rewardRepository, UserRepository userRepository,
            GroupRepository groupRepository) {
        this.rewardRepository = rewardRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    public Reward createReward(CreateRewardRequest request, String creatorEmail) {
        User creator = userRepository.findByEmail(creatorEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Group group = null;
        if (request.getGroupId() != null) {
            group = groupRepository.findById(request.getGroupId())
                    .orElseThrow(() -> new ResourceNotFoundException("Grupo no encontrado"));
        }

        Reward reward = new Reward(
                request.getName(),
                request.getDescription(),
                request.getPointsCost(),
                group,
                creator);

        return rewardRepository.save(reward);
    }

    public List<Reward> getRewardsByGroup(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Grupo no encontrado"));

        return rewardRepository.findByGroup(group);
    }

    public List<Reward> getPersonalRewards(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        return rewardRepository.findByGroupIsNullAndCreatedBy(user);
    }

    @Transactional
    public void claimReward(Long rewardId, String claimantEmail) {
        User claimant = userRepository.findByEmail(claimantEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new ResourceNotFoundException("Recompensa no encontrada"));

        if (claimant.getAccumulatedPoints() < reward.getPointsCost()) {
            throw new ForbiddenActionException("No tienes suficientes puntos para reclamar esta recompensa");
        }

        claimant.setAccumulatedPoints(claimant.getAccumulatedPoints() - reward.getPointsCost());
        userRepository.save(claimant);
    }

    public Reward editReward(Long rewardId, UpdateRewardRequest request, String editorEmail) {
        User editor = userRepository.findByEmail(editorEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new ResourceNotFoundException("Recompensa no encontrada"));

        if (!reward.getCreatedBy().getId().equals(editor.getId())) {
            throw new ForbiddenActionException("No tienes permisos para editar esta recompensa");
        }

        reward.setName(request.getName());
        reward.setDescription(request.getDescription());
        reward.setPointsCost(request.getPointsCost());

        return rewardRepository.save(reward);
    }

    public void deleteReward(Long rewardId, String deleterEmail) {
        User deleter = userRepository.findByEmail(deleterEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new ResourceNotFoundException("Recompensa no encontrada"));

        if (!reward.getCreatedBy().getId().equals(deleter.getId())) {
            throw new ForbiddenActionException("No tienes permisos para eliminar esta recompensa");
        }

        rewardRepository.delete(reward);
    }
}
