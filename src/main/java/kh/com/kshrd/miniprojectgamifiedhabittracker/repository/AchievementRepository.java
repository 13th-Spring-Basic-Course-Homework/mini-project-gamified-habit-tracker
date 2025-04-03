package kh.com.kshrd.miniprojectgamifiedhabittracker.repository;

import kh.com.kshrd.miniprojectgamifiedhabittracker.model.Achievement;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AchievementRepository {

    @Results(id = "achievementMapper", value = {
        @Result(property = "achievementId", column = "achievement_id"),
        @Result(property = "xpRequired", column = "xp_required"),
    })
    @Select("""
        SELECT * FROM achievements;
    """)
    List<Achievement> getAllAchievements();

    @ResultMap("achievementMapper")
    @Select("""
        SELECT * FROM achievements
        OFFSET #{page} LIMIT #{size}
    """)
    List<Achievement> getAllAchievementsWithPagination(Integer page, Integer size);
}
