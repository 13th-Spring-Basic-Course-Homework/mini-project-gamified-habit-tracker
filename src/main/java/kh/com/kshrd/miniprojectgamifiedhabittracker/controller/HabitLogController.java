package kh.com.kshrd.miniprojectgamifiedhabittracker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.request.HabitLogRequest;
import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.response.APIResponse;
import kh.com.kshrd.miniprojectgamifiedhabittracker.model.HabitLog;
import kh.com.kshrd.miniprojectgamifiedhabittracker.service.HabitLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/habit-logs")
@SecurityRequirement(name = "bearerAuth")
public class HabitLogController {

    private final HabitLogService habitLogService;

    @Operation(summary = "Create a new habit log", description = "Creates a new log for a specific habit with the provided details.")
    @PostMapping
    public ResponseEntity<APIResponse<HabitLog>> createHabitLog(@RequestBody @Valid HabitLogRequest request) {
        APIResponse<HabitLog> apiResponse = APIResponse.<HabitLog>builder()
                .success(true)
                .message("Habit log created successfully!")
                .status(HttpStatus.CREATED)
                .payload(habitLogService.createHabitLog(request))
                .timestamps(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @Operation(summary = "Get all habit logs by habit ID", description = "Fetches a paginated list of all habit logs for a specific habit by its ID.")
    @GetMapping("/{habit-id}")
    public ResponseEntity<APIResponse<List<HabitLog>>> getAllHabitLogsById(@PathVariable("habit-id") UUID habitId,
                                                                           @RequestParam(defaultValue = "1") @Positive Integer page,
                                                                           @RequestParam(defaultValue = "10") @Positive Integer size) {
        APIResponse<List<HabitLog>> apiResponse = APIResponse.<List<HabitLog>>builder()
                .success(true)
                .message("Fetched all habit logs for the specified habit ID successfully!")
                .status(HttpStatus.OK)
                .payload(habitLogService.getAllHabitLogsById(habitId, page, size))
                .timestamps(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

}
