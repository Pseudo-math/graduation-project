-- V2.0: Создание новой схемы для универсальных пользователей и ролей

-- Таблица для всех пользователей, которые могут входить в систему
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password_digest VARCHAR(255) NOT NULL
    -- Примечание: Hibernate называет поле 'passwordDigest', но в БД лучше использовать 'password_hash' или 'password'
);

-- Справочная таблица для ролей
CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255)
);

-- Связующая таблица для отношения "многие-ко-многим" между пользователями и ролями
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role_id BIGINT NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

-- Таблица для хранения специфичных данных менеджеров
CREATE TABLE manager_profiles (
    user_id BIGINT PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE
    -- Эта таблица использует общий первичный ключ с таблицей users
);

-- Связующая таблица для отношения "многие-ко-многим" между профилями менеджеров и предприятиями
CREATE TABLE manager_enterprises (
    manager_profile_id BIGINT NOT NULL REFERENCES manager_profiles(user_id) ON DELETE CASCADE,
    enterprise_id BIGINT NOT NULL REFERENCES enterprises(id) ON DELETE CASCADE, -- Убедитесь, что ваша таблица предприятий называется 'enterprises'
    PRIMARY KEY (manager_profile_id, enterprise_id)
);