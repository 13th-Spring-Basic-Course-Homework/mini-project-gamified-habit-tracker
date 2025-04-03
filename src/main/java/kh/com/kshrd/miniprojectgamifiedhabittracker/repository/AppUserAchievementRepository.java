package kh.com.kshrd.miniprojectgamifiedhabittracker.repository;

import kh.com.kshrd.miniprojectgamifiedhabittracker.model.Achievement;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;


@Mapper
public interface AppUserAchievementRepository {

    @Insert("""
        INSERT INTO app_user_achievements
        VALUES (default, #{appUserId}, #{achievementId})
    """)
    void insertAppUserIdAndAchievementId(UUID appUserId, UUID achievementId);

    @Results(id = "achievementMapper", value = {
            @Result(property = "achievementId", column = "achievement_id"),
            @Result(property = "xpRequired", column = "xp_required"),
    })
    @Select("""
        SELECT * FROM achievements a
        INNER JOIN app_user_achievements aua
        ON a.achievement_id = aua.achievement_id
        WHERE app_user_id = #{appUserId};
    """)
    List<Achievement> getAllAchievementsByAppUserId(UUID appUserId);

    @ResultMap("achievementMapper")
    @Select("""
        SELECT * FROM achievements a
        INNER JOIN app_user_achievements aua
        ON a.achievement_id = aua.achievement_id
        WHERE app_user_id = #{appUserId}
        OFFSET #{page} LIMIT #{size};
    """)
    List<Achievement> getAllAchievementsByAppUserIdWithPagination(UUID appUserId, Integer page, Integer size);
}
