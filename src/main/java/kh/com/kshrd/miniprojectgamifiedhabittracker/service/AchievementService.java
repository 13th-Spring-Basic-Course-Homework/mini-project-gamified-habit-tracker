package kh.com.kshrd.miniprojectgamifiedhabittracker.service;


import jakarta.validation.constraints.Positive;
import kh.com.kshrd.miniprojectgamifiedhabittracker.model.Achievement;

import java.util.List;


public interface AchievementService {
    List<Achievement> getAllAchievements(Integer page, Integer size);

    List<Achievement> getAllAchievementsAppUserId(@Positive Integer page, @Positive Integer size);
}
