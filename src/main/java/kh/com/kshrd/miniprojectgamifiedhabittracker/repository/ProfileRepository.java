package kh.com.kshrd.miniprojectgamifiedhabittracker.repository;

import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.request.ProfileRequest;
import kh.com.kshrd.miniprojectgamifiedhabittracker.model.AppUser;
import org.apache.ibatis.annotations.*;

import java.util.UUID;

@Mapper
public interface ProfileRepository {

    @Results(id = "appUserMapper", value = {
            @Result(property = "appUserId", column = "app_user_id"),
            @Result(property = "profileImageUrl", column = "profile_image_url"),
            @Result(property = "isVerified", column = "is_verified"),
            @Result(property = "createdAt", column = "created_at")
    })
    @Select("""
        UPDATE app_users
        SET username = #{request.username}, profile_image_url = #{request.profileImageUrl}
        WHERE app_user_id = #{appUserId}
        RETURNING *;
    """)
    AppUser updateProfile(UUID appUserId, @Param("request") ProfileRequest request);


    @Delete("""
        DELETE FROM app_users
        WHERE app_user_id = #{appUserId};
    """)
    void deleteProfile(UUID appUserId);
}
