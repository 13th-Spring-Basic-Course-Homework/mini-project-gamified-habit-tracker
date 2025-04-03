package kh.com.kshrd.miniprojectgamifiedhabittracker.model;

import kh.com.kshrd.miniprojectgamifiedhabittracker.enums.HabitLogStatus;
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
public class HabitLog {

    private UUID habitLogId;
    private LocalDate logDate;
    private HabitLogStatus status;
    private Integer xpEarned;
    private Habit habit;

}
