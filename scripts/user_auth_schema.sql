-- MySQL: create database user_auth + starter tables for auth.
-- Apply: mysql -u root -p < scripts/user_auth_schema.sql
-- Hibernate `ddl-auto: update` can create/alter tables from @Entity mappings later — this matches a typical manually-managed baseline.

CREATE DATABASE IF NOT EXISTS user_auth
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE user_auth;

-- ---------------------------------------------------------------------------
-- Optional starter tables (no JPA entities in this repo yet — adjust freely)
-- ---------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS users (
  id            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  email         VARCHAR(255)    NOT NULL,
  password_hash VARCHAR(255)    NOT NULL,
  enabled       TINYINT(1)      NOT NULL DEFAULT 1,
  created_at    TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at    TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY uk_users_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS roles (
  id   BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(64)     NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_roles_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS user_roles (
  user_id BIGINT UNSIGNED NOT NULL,
  role_id BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (user_id, role_id),
  CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users (id)
    ON DELETE CASCADE,
  CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles (id)
    ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
