CREATE TABLE IF NOT EXISTS `users`
(
    `id`                  BIGINT(20) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    `first_name`          VARCHAR(256) NOT NULL,
    `last_name`           VARCHAR(256) NOT NULL,
    `email`               VARCHAR(512) NOT NULL UNIQUE,
    `password_hash`       VARCHAR(512) NOT NULL,
    `login_failure_count` INT          NOT NULL DEFAULT 0
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS `login_histories`
(
    `id`           BIGINT(20) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    `user_id`      BIGINT(20) UNSIGNED NOT NULL,
    `logged_in_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(user_id) REFERENCES users(id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
