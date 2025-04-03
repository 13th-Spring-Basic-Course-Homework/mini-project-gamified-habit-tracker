package kh.com.kshrd.miniprojectgamifiedhabittracker.service.impl;

import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.request.AppUserRequest;
import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.request.ProfileRequest;
import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.response.AppUserResponse;
import kh.com.kshrd.miniprojectgamifiedhabittracker.model.AppUser;
import kh.com.kshrd.miniprojectgamifiedhabittracker.repository.ProfileRepository;
import kh.com.kshrd.miniprojectgamifiedhabittracker.service.AuthService;
import kh.com.kshrd.miniprojectgamifiedhabittracker.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final AuthService authService;
    private final ModelMapper modelMapper;
    private final ProfileRepository profileRepository;

    @Override
    public AppUserResponse getProfile() {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return modelMapper.map(appUser, AppUserResponse.class);
    }

    @Override
    public AppUserResponse updateProfile(ProfileRequest request) {
        UUID appUserId = authService.getCurrentAppUserId();
        AppUser appUser = profileRepository.updateProfile(appUserId, request);
        return modelMapper.map(appUser, AppUserResponse.class);
    }

    @Override
    public void deleteProfile() {
        UUID appUserId = authService.getCurrentAppUserId();
        profileRepository.deleteProfile(appUserId);
    }

}
