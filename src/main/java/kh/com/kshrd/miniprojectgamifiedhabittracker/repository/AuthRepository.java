package kh.com.kshrd.miniprojectgamifiedhabittracker.repository;

import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.request.AppUserRequest;
import kh.com.kshrd.miniprojectgamifiedhabittracker.model.AppUser;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AuthRepository {

    @Results(id = "authMapper", value = {
            @Result(property = "appUserId", column = "app_user_id"),
            @Result(property = "profileImageUrl", column = "profile_image_url"),
            @Result(property = "isVerified", column = "is_verified"),
            @Result(property = "createdAt", column = "created_at")
    })
    @Select("""
        INSERT INTO app_users
        VALUES (default, #{request.username}, #{request.email}, #{request.password}, default, default, #{request.profileImageUrl}, default, default)
        RETURNING *;
    """)
    AppUser register(@Param("request") AppUserRequest request);
}
