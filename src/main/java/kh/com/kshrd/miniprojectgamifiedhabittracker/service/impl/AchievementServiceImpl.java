package kh.com.kshrd.miniprojectgamifiedhabittracker.service.impl;

import kh.com.kshrd.miniprojectgamifiedhabittracker.model.Achievement;
import kh.com.kshrd.miniprojectgamifiedhabittracker.repository.AchievementRepository;
import kh.com.kshrd.miniprojectgamifiedhabittracker.repository.AppUserAchievementRepository;
import kh.com.kshrd.miniprojectgamifiedhabittracker.service.AchievementService;
import kh.com.kshrd.miniprojectgamifiedhabittracker.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository achievementRepository;
    private final AuthService authService;
    private final AppUserAchievementRepository appUserAchievementRepository;

    @Override
    public List<Achievement> getAllAchievements(Integer page, Integer size) {
        page = (page - 1) * size;
        return achievementRepository.getAllAchievementsWithPagination(page, size);
    }

    @Override
    public List<Achievement> getAllAchievementsAppUserId(Integer page, Integer size) {
        page = (page - 1) * size;
        UUID appUserId = authService.getCurrentAppUserId();
        return appUserAchievementRepository.getAllAchievementsByAppUserIdWithPagination(appUserId, page, size);
    }
}
