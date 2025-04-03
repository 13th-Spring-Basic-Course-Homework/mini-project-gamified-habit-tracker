package kh.com.kshrd.miniprojectgamifiedhabittracker.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @NotNull
    @NotBlank
    @Schema(defaultValue = "sovannak")
    private String identifier;

    @NotNull
    @NotBlank
    @Schema(defaultValue = "Fire@0309")
    private String password;
}
