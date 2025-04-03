package kh.com.kshrd.miniprojectgamifiedhabittracker.service.impl;

import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.request.HabitRequest;
import kh.com.kshrd.miniprojectgamifiedhabittracker.exception.NotFoundException;
import kh.com.kshrd.miniprojectgamifiedhabittracker.model.Habit;
import kh.com.kshrd.miniprojectgamifiedhabittracker.repository.HabitRepository;
import kh.com.kshrd.miniprojectgamifiedhabittracker.service.AuthService;
import kh.com.kshrd.miniprojectgamifiedhabittracker.service.HabitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HabitServiceImpl implements HabitService {

    private final HabitRepository habitRepository;
    private final AuthService authService;

    @Override
    public Habit createHabit(HabitRequest request) {
        UUID appUserId = authService.getCurrentAppUserId();
        return habitRepository.createHabit(appUserId, request);
    }

    @Override
    public List<Habit> getAllHabits(Integer page, Integer size) {
        UUID appUserId = authService.getCurrentAppUserId();
        page = (page - 1) * size;
        return habitRepository.getAllHabits(appUserId, page, size);
    }

    @Override
    public Habit getHabitById(UUID habitId) {
        UUID appUserId = authService.getCurrentAppUserId();
        Habit habit = habitRepository.getHabitById(appUserId, habitId);
        if (habit == null){
            throw new NotFoundException("Habit with ID " + habitId + " not found");
        }
        return habit;
    }

    @Override
    public Habit updateHabitById(UUID habitId, HabitRequest request) {
        getHabitById(habitId);
        UUID appUserId = authService.getCurrentAppUserId();
        return habitRepository.updateHabitById(appUserId, habitId, request);
    }

    @Override
    public void deleteHabitById(UUID habitId) {
        getHabitById(habitId);
        UUID appUserId = authService.getCurrentAppUserId();
        habitRepository.deleteHabitById(appUserId, habitId);
    }

}
