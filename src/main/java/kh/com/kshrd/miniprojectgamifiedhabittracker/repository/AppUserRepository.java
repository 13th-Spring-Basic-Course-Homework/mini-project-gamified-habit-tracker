package kh.com.kshrd.miniprojectgamifiedhabittracker.repository;

import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.response.AppUserResponse;
import kh.com.kshrd.miniprojectgamifiedhabittracker.model.AppUser;
import org.apache.ibatis.annotations.*;

import java.util.UUID;

@Mapper
public interface AppUserRepository {

    @Results(id = "appUserMapper", value = {
            @Result(property = "appUserId", column = "app_user_id"),
            @Result(property = "profileImageUrl", column = "profile_image_url"),
            @Result(property = "isVerified", column = "is_verified"),
            @Result(property = "createdAt", column = "created_at")
    })
    @Select("""
                SELECT * FROM app_users
                WHERE username = #{identifier} OR email = #{identifier};
            """)
    AppUser getUserByIdentifier(String identifier);

    @Update("""
                UPDATE app_users
                SET is_verified = true;
            """)
    void updateVerify();

    @Delete("""
        DELETE FROM app_users
        WHERE app_user_id = #{appUserId};
    """)
    void removeAppUserById(UUID appUserId);

    @Results(id = "appUserResponseMapper", value = {
            @Result(property = "appUserId", column = "app_user_id"),
            @Result(property = "profileImageUrl", column = "profile_image_url"),
            @Result(property = "isVerified", column = "is_verified"),
            @Result(property = "createdAt", column = "created_at")
    })
    @Select("""
                SELECT * FROM app_users
                WHERE app_user_id = #{appUserId};
            """)
    AppUserResponse getAppUserById(UUID appUserId);

    @Select("""
        UPDATE app_users
        SET xp = xp + #{exEarned}
        WHERE app_user_id = #{appUserId}
        RETURNING xp;
    """)
    Integer updateXp(UUID appUserId, int exEarned);

    @Update("""
        UPDATE app_users
        SET level = xp / 100
        WHERE app_user_id = #{appUserId};
    """)
    void updateLevel(UUID appUserId);
}
