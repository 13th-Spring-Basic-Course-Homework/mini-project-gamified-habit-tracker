package kh.com.kshrd.miniprojectgamifiedhabittracker;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Gamified Habit Tracker API",
                version = "1.0",
                description = "API documentation for the Gamified Habit Tracker application"
        )
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        in = SecuritySchemeIn.HEADER
)
public class MiniProjectGamifiedHabitTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniProjectGamifiedHabitTrackerApplication.class, args);
    }

}
