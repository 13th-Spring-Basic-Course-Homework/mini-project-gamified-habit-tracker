package kh.com.kshrd.miniprojectgamifiedhabittracker.service;

import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.request.HabitLogRequest;
import kh.com.kshrd.miniprojectgamifiedhabittracker.model.HabitLog;
import org.checkerframework.checker.index.qual.Positive;

import java.util.List;
import java.util.UUID;

public interface HabitLogService {

    HabitLog createHabitLog(HabitLogRequest request);

    List<HabitLog> getAllHabitLogsById(UUID habitId, Integer page, Integer size);
}
