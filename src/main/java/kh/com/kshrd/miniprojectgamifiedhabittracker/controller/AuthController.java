package kh.com.kshrd.miniprojectgamifiedhabittracker.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.request.AppUserRequest;
import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.request.AuthRequest;
import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.response.APIResponse;
import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.response.AppUserResponse;
import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.response.TokenResponse;
import kh.com.kshrd.miniprojectgamifiedhabittracker.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import java.time.LocalDateTime;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auths")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Register a new user", description = "Registers a new user and returns the user details upon successful registration.")
    @PostMapping("/register")
    public ResponseEntity<APIResponse<AppUserResponse>> register(@RequestBody @Valid AppUserRequest request) {
        APIResponse<AppUserResponse> apiResponse = APIResponse.<AppUserResponse>builder()
                .success(true)
                .message("User registered successfully! Please verify your email to complete the registration.")
                .status(HttpStatus.CREATED)
                .payload(authService.register(request))
                .timestamps(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @Operation(summary = "User login", description = "Authenticates the user and provides an authentication token.")
    @PostMapping("/login")
    public ResponseEntity<APIResponse<TokenResponse>> login(@RequestBody @Valid AuthRequest request) {
        APIResponse<TokenResponse> apiResponse = APIResponse.<TokenResponse>builder()
                .success(true)
                .message("Login successful! Authentication token generated.")
                .status(HttpStatus.OK)
                .payload(authService.login(request))
                .timestamps(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @Operation(summary = "Verify email with OTP", description = "Verifies the user's email address using the provided OTP.")
    @PostMapping("/verify")
    public ResponseEntity<APIResponse<?>> verify(@RequestParam String email, @RequestParam String otp) {
        authService.verify(email, otp);
        APIResponse<?> apiResponse = APIResponse.builder()
                .success(true)
                .message("Email successfully verified! You can now log in.")
                .status(HttpStatus.OK)
                .payload(null)
                .timestamps(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @Operation(summary = "Resend verification OTP", description = "Sends a new OTP to the user's email for verification.")
    @PostMapping("/resend")
    public ResponseEntity<APIResponse<?>> resend(@RequestParam String email){
        authService.resend(email);
        APIResponse<?> apiResponse = APIResponse.builder()
                .success(true)
                .message("Verification OTP successfully resent to your email.")
                .status(HttpStatus.CREATED)
                .payload(null)
                .timestamps(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}


