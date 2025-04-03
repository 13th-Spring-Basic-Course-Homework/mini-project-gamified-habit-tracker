package kh.com.kshrd.miniprojectgamifiedhabittracker.repository;

import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.request.HabitRequest;
import kh.com.kshrd.miniprojectgamifiedhabittracker.model.Habit;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface HabitRepository {

    @Results(id = "habitMapper", value = {
            @Result(property = "habitId", column = "habit_id"),
            @Result(property = "isActive", column = "is_active"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "appUserResponse", column = "app_user_id", one = @One(
                    select = "kh.com.kshrd.miniprojectgamifiedhabittracker.repository.AppUserRepository.getAppUserById"
            ))
    })
    @Select("""
                INSERT INTO habits
                VALUES (default, #{request.title}, #{request.description}, #{request.frequency}, default, default, #{appUserId})
                RETURNING *;
            """)
    Habit createHabit(UUID appUserId, @Param("request") HabitRequest request);


    @ResultMap("habitMapper")
    @Select("""
                SELECT * FROM habits
                WHERE app_user_id = #{appUserId}
                OFFSET #{page} LIMIT #{size};
            """)
    List<Habit> getAllHabits(UUID appUserId, Integer page, Integer size);

    @ResultMap("habitMapper")
    @Select("""
                SELECT * FROM habits
                WHERE app_user_id = #{appUserId}
                AND habit_id = #{habitId};
            """)
    Habit getHabitById(UUID appUserId, UUID habitId);

    @ResultMap("habitMapper")
    @Select("""
                UPDATE habits
                SET title = #{request.title}, description = #{request.description}, frequency = #{request.frequency}
                WHERE app_user_id = #{appUserId}
                AND habit_id = #{habitId}
                RETURNING *;
            """)
    Habit updateHabitById(UUID appUserId, UUID habitId, @Param("request") HabitRequest request);

    @Delete("""
                DELETE FROM habits
                WHERE app_user_id = #{appUserId}
                AND habit_id = #{habitId};
            """)
    void deleteHabitById(UUID appUserId, UUID habitId);

    @ResultMap("habitMapper")
    @Select("""
                SELECT * FROM habits
                WHERE habit_id = #{habitId};
            """)
    Habit getHabitByIdForHabitLog(UUID habitId);
}
