CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS app_users
(
    app_user_id       UUID PRIMARY KEY DEFAULT UUID_GENERATE_V4(),
    username          VARCHAR(50)  NOT NULL UNIQUE,
    email             VARCHAR(100) NOT NULL UNIQUE,
    password          VARCHAR(255) NOT NULL,
    level             INT              DEFAULT 1,
    xp                INT              DEFAULT 0,
    profile_image_url VARCHAR(255),
    is_verified       BOOLEAN          DEFAULT FALSE,
    created_at        TIMESTAMP        DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS habits
(
    habit_id    UUID PRIMARY KEY DEFAULT UUID_GENERATE_V4(),
    title       VARCHAR(100) NOT NULL,
    description TEXT,
    frequency   VARCHAR(20) CHECK (frequency IN ('DAILY', 'WEEKLY', 'MONTHLY')),
    created_at  TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    is_active   BOOLEAN          DEFAULT TRUE,
    app_user_id UUID         NOT NULL,
    CONSTRAINT fk_habit_user FOREIGN KEY (app_user_id) REFERENCES app_users (app_user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS habit_logs
(
    habit_log_id UUID PRIMARY KEY DEFAULT UUID_GENERATE_V4(),
    log_date     DATE NOT NULL    DEFAULT CURRENT_DATE,
    status       VARCHAR(20) CHECK (status IN ('COMPLETED', 'MISSED')),
    xp_earned    INT              DEFAULT 0,
    habit_id     UUID NOT NULL,
    CONSTRAINT fk_log_habit FOREIGN KEY (habit_id) REFERENCES habits (habit_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS achievements
(
    achievement_id UUID PRIMARY KEY DEFAULT UUID_GENERATE_V4(),
    title          VARCHAR(100) NOT NULL,
    description    TEXT,
    badge          VARCHAR(255) NOT NULL,
    xp_required    INT          NOT NULL
);

CREATE TABLE IF NOT EXISTS app_user_achievements
(
    app_user_achievement_id UUID PRIMARY KEY DEFAULT UUID_GENERATE_V4(),
    app_user_id             UUID NOT NULL,
    achievement_id          UUID NOT NULL,
    CONSTRAINT fk_ua_user FOREIGN KEY (app_user_id) REFERENCES app_users (app_user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_ua_achievement FOREIGN KEY (achievement_id) REFERENCES achievements (achievement_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT unique_user_achievement UNIQUE (app_user_id, achievement_id)
);
