package kh.com.kshrd.miniprojectgamifiedhabittracker.service;

import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.request.AppUserRequest;
import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.request.AuthRequest;
import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.response.AppUserResponse;
import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.response.TokenResponse;

import java.util.UUID;


public interface AuthService {

    AppUserResponse register(AppUserRequest request);

    TokenResponse login(AuthRequest request);

    void verify(String email, String otp);

    void resend(String email);

    UUID getCurrentAppUserId();


}
