package kh.com.kshrd.miniprojectgamifiedhabittracker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.request.HabitRequest;
import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.response.APIResponse;
import kh.com.kshrd.miniprojectgamifiedhabittracker.model.Habit;
import kh.com.kshrd.miniprojectgamifiedhabittracker.service.HabitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/habits")
@SecurityRequirement(name = "bearerAuth")
public class HabitController {

    private final HabitService habitService;

    @Operation(summary = "Create a new habit", description = "Creates a new habit with the provided details.")
    @PostMapping
    public ResponseEntity<APIResponse<Habit>> createHabit(@RequestBody @Valid HabitRequest request) {
        APIResponse<Habit> apiResponse = APIResponse.<Habit>builder()
                .success(true)
                .message("Habit created successfully!")
                .status(HttpStatus.CREATED)
                .payload(habitService.createHabit(request))
                .timestamps(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @Operation(summary = "Get all habits", description = "Fetches a paginated list of all habits.")
    @GetMapping
    public ResponseEntity<APIResponse<List<Habit>>> getAllHabits(@RequestParam(defaultValue = "1") @Positive Integer page, @RequestParam(defaultValue = "10") @Positive Integer size) {
        APIResponse<List<Habit>> apiResponse = APIResponse.<List<Habit>>builder()
                .success(true)
                .message("Fetched all habits successfully!")
                .status(HttpStatus.OK)
                .payload(habitService.getAllHabits(page, size))
                .timestamps(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @Operation(summary = "Get habit by ID", description = "Fetches a specific habit by its ID.")
    @GetMapping("/{habit-id}")
    public ResponseEntity<APIResponse<Habit>> getHabitById(@PathVariable("habit-id") @NotNull UUID habitId) {
        APIResponse<Habit> apiResponse = APIResponse.<Habit>builder()
                .success(true)
                .message("Habit fetched successfully!")
                .status(HttpStatus.OK)
                .payload(habitService.getHabitById(habitId))
                .timestamps(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @Operation(summary = "Update habit by ID", description = "Updates the details of an existing habit by its ID.")
    @PutMapping("/{habit-id}")
    public ResponseEntity<APIResponse<Habit>> updateHabitById(@PathVariable("habit-id") @NotNull UUID habitId, @RequestBody @Valid HabitRequest request) {
        APIResponse<Habit> apiResponse = APIResponse.<Habit>builder()
                .success(true)
                .message("Habit updated successfully!")
                .status(HttpStatus.OK)
                .payload(habitService.updateHabitById(habitId, request))
                .timestamps(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @Operation(summary = "Delete habit by ID", description = "Deletes a habit by its ID.")
    @DeleteMapping("/{habit-id}")
    public ResponseEntity<APIResponse<Habit>> deleteHabitById(@PathVariable("habit-id") @NotNull UUID habitId) {
        habitService.deleteHabitById(habitId);
        APIResponse<Habit> apiResponse = APIResponse.<Habit>builder()
                .success(true)
                .message("Habit deleted successfully!")
                .status(HttpStatus.OK)
                .payload(null)
                .timestamps(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

}
