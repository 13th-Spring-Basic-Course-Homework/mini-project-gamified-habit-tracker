package kh.com.kshrd.miniprojectgamifiedhabittracker.service;

import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.request.HabitRequest;
import kh.com.kshrd.miniprojectgamifiedhabittracker.model.Habit;

import java.util.List;
import java.util.UUID;

public interface HabitService {

    Habit createHabit(HabitRequest request);

    List<Habit> getAllHabits(Integer page, Integer size);

    Habit getHabitById(UUID habitId);

    Habit updateHabitById(UUID habitId, HabitRequest request);

    void deleteHabitById(UUID habitId);

}
