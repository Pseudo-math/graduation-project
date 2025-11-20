INSERT INTO roles (name, description)
VALUES ('ROLE_USER', 'Regular user with basic permissions')
ON CONFLICT (name) DO NOTHING;

-- Переменные для удобства
WITH
    -- 1. Создаем самого пользователя в таблице 'users' и получаем его ID
    new_user AS (
        INSERT INTO users (username, password_digest)
        VALUES ('simple_user', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IKbeum83U/FAPOZbGisP3T/0Yh/f3q') -- Важно: здесь должен быть ЗАХЭШИРОВАННЫЙ пароль!
        RETURNING id
    ),
    -- 2. Находим ID роли 'ROLE_USER'
    user_role AS (
        SELECT id FROM roles WHERE name = 'ROLE_USER'
    )
-- 3. Связываем нового пользователя с ролью в таблице 'user_roles'
INSERT INTO user_roles (user_id, role_id)
SELECT new_user.id, user_role.id
FROM new_user, user_role;