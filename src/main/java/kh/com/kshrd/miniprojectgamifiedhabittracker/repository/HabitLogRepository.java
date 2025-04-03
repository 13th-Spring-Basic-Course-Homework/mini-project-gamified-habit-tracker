package kh.com.kshrd.miniprojectgamifiedhabittracker.repository;

import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.request.HabitLogRequest;
import kh.com.kshrd.miniprojectgamifiedhabittracker.model.HabitLog;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface HabitLogRepository {

    @Results(id = "habitLogMapper", value = {
            @Result(property = "habitLogId", column = "habit_log_id"),
            @Result(property = "logDate", column = "log_date"),
            @Result(property = "xpEarned", column = "xp_earned"),
            @Result(property = "habit", column = "habit_id", one = @One(
                    select = "kh.com.kshrd.miniprojectgamifiedhabittracker.repository.HabitRepository.getHabitByIdForHabitLog"
            ))
    })
    @Select("""
        INSERT INTO habit_logs
        VALUES (default, default, #{request.status}, #{xpEarned}, #{request.habitId})
        RETURNING *;
    """)
    HabitLog createHabitLog(@Param("request") HabitLogRequest request, Integer xpEarned);

    @ResultMap("habitLogMapper")
    @Select("""
        SELECT * FROM habit_logs
        WHERE habit_id = #{habitId}
        OFFSET #{page} LIMIT #{size}
    """)
    List<HabitLog> getAllHabitLogsById(UUID habitId, Integer page, Integer size);
}
