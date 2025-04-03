package kh.com.kshrd.miniprojectgamifiedhabittracker.service.impl;

import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.request.HabitLogRequest;
import kh.com.kshrd.miniprojectgamifiedhabittracker.enums.HabitLogStatus;
import kh.com.kshrd.miniprojectgamifiedhabittracker.model.Achievement;
import kh.com.kshrd.miniprojectgamifiedhabittracker.model.HabitLog;
import kh.com.kshrd.miniprojectgamifiedhabittracker.repository.AchievementRepository;
import kh.com.kshrd.miniprojectgamifiedhabittracker.repository.AppUserAchievementRepository;
import kh.com.kshrd.miniprojectgamifiedhabittracker.repository.AppUserRepository;
import kh.com.kshrd.miniprojectgamifiedhabittracker.repository.HabitLogRepository;
import kh.com.kshrd.miniprojectgamifiedhabittracker.service.AuthService;
import kh.com.kshrd.miniprojectgamifiedhabittracker.service.HabitLogService;
import kh.com.kshrd.miniprojectgamifiedhabittracker.service.HabitService;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.index.qual.Positive;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HabitLogServiceImpl implements HabitLogService {

    private final HabitLogRepository habitRepository;
    private final HabitService habitService;
    private final AppUserRepository appUserRepository;
    private final AuthService authService;
    private final AchievementRepository achievementRepository;
    private final AppUserAchievementRepository appUserAchievementRepository;

    @Override
    public HabitLog createHabitLog(HabitLogRequest request) {
        UUID appUserId = authService.getCurrentAppUserId();
        habitService.getHabitById(request.getHabitId());
        int exEarned = request.getStatus() == HabitLogStatus.COMPLETED ? 10 : 0;
        int xp = appUserRepository.updateXp(appUserId, exEarned);
        appUserRepository.updateLevel(appUserId);
        List<Achievement> appUserAchievements = appUserAchievementRepository.getAllAchievementsByAppUserId(appUserId);
        List<Achievement> achievements = achievementRepository.getAllAchievements();
        for (Achievement achievement : achievements) {
            if (xp >= achievement.getXpRequired() && !appUserAchievements.contains(achievement)) {
                appUserAchievementRepository.insertAppUserIdAndAchievementId(appUserId, achievement.getAchievementId());
            }
        }
        return habitRepository.createHabitLog(request, exEarned);
    }

    @Override
    public List<HabitLog> getAllHabitLogsById(UUID habitId, Integer page, Integer size) {
        page = (page - 1) * size;
        habitService.getHabitById(habitId);
        return habitRepository.getAllHabitLogsById(habitId, page, size);
    }
}
