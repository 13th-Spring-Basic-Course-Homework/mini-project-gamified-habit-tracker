package kh.com.kshrd.miniprojectgamifiedhabittracker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.request.ProfileRequest;
import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.response.APIResponse;
import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.response.AppUserResponse;
import kh.com.kshrd.miniprojectgamifiedhabittracker.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/profiles")
@SecurityRequirement(name = "bearerAuth")
public class ProfileController {

    private final ProfileService profileService;

    @Operation(summary = "Get user profile", description = "Fetches the details of the currently authenticated user.")
    @GetMapping
    public ResponseEntity<APIResponse<AppUserResponse>> getProfile() {
        APIResponse<AppUserResponse> apiResponse = APIResponse.<AppUserResponse>builder()
                .success(true)
                .message("User profile fetched successfully!")
                .status(HttpStatus.OK)
                .payload(profileService.getProfile())
                .timestamps(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @Operation(summary = "Update user profile", description = "Updates the details of the currently authenticated user. Provide the necessary fields in the request body.")
    @PutMapping
    public ResponseEntity<APIResponse<AppUserResponse>> updateProfile(@RequestBody ProfileRequest request) {
        APIResponse<AppUserResponse> apiResponse = APIResponse.<AppUserResponse>builder()
                .success(true)
                .message("User profile updated successfully!")
                .status(HttpStatus.OK)
                .payload(profileService.updateProfile(request))
                .timestamps(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @Operation(summary = "Delete user profile", description = "Deletes the currently authenticated user's profile. This action cannot be undone.")
    @DeleteMapping
    public ResponseEntity<APIResponse<?>> deleteProfile() {
        profileService.deleteProfile();
        APIResponse<?> apiResponse = APIResponse.builder()
                .success(true)
                .message("User profile deleted successfully!")
                .status(HttpStatus.OK)
                .payload(null)
                .timestamps(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

}
