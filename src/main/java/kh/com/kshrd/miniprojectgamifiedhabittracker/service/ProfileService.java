package kh.com.kshrd.miniprojectgamifiedhabittracker.service;

import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.request.ProfileRequest;
import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.response.AppUserResponse;

public interface ProfileService {

    AppUserResponse getProfile();

    AppUserResponse updateProfile(ProfileRequest request);

    void deleteProfile();
}
