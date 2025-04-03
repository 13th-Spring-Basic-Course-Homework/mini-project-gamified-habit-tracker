package kh.com.kshrd.miniprojectgamifiedhabittracker.model;

import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.response.AppUserResponse;
import kh.com.kshrd.miniprojectgamifiedhabittracker.enums.HabitFrequency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Habit {

    private UUID habitId;
    private String title;
    private String description;
    private HabitFrequency frequency;
    private Boolean isActive;
    private AppUserResponse appUserResponse;
    private LocalDateTime createdAt;

}
