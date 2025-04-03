package kh.com.kshrd.miniprojectgamifiedhabittracker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Positive;
import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.response.APIResponse;
import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.response.AppUserResponse;
import kh.com.kshrd.miniprojectgamifiedhabittracker.model.Achievement;
import kh.com.kshrd.miniprojectgamifiedhabittracker.model.HabitLog;
import kh.com.kshrd.miniprojectgamifiedhabittracker.service.AchievementService;
import kh.com.kshrd.miniprojectgamifiedhabittracker.service.impl.AchievementServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/achievements")
@SecurityRequirement(name = "bearerAuth")
public class AchievementController {

    private final AchievementService achievementService;

    @Operation(summary = "Get all achievements", description = "Fetches a paginated list of all achievements.")
    @GetMapping
    public ResponseEntity<APIResponse<List<Achievement>>> getAllAchievements(@RequestParam(defaultValue = "1") @Positive Integer page, @RequestParam(defaultValue = "10") @Positive Integer size) {
        APIResponse<List<Achievement>> apiResponse = APIResponse.<List<Achievement>>builder()
                .success(true)
                .message("Achievements retrieved successfully!")
                .status(HttpStatus.OK)
                .payload(achievementService.getAllAchievements(page, size))
                .timestamps(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @Operation(summary = "Get achievements by App User ID", description = "Fetches a paginated list of achievements filtered by a specific App User ID.")
    @GetMapping("/app-users")
    public ResponseEntity<APIResponse<List<Achievement>>> getAllAchievementsByAppUserId(@RequestParam(defaultValue = "1") @Positive Integer page, @RequestParam(defaultValue = "10") @Positive Integer size) {
        APIResponse<List<Achievement>> apiResponse = APIResponse.<List<Achievement>>builder()
                .success(true)
                .message("Achievements for the specified App User retrieved successfully!")
                .status(HttpStatus.OK)
                .payload(achievementService.getAllAchievementsAppUserId(page, size))
                .timestamps(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

}
