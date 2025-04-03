package kh.com.kshrd.miniprojectgamifiedhabittracker.dto.request;

import jakarta.validation.constraints.NotNull;
import kh.com.kshrd.miniprojectgamifiedhabittracker.enums.HabitLogStatus;
import kh.com.kshrd.miniprojectgamifiedhabittracker.model.Habit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HabitLogRequest {

    @NotNull
    private HabitLogStatus status;

    @NotNull
    private UUID habitId;

}
