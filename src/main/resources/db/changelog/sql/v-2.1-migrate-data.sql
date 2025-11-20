-- V2.1: Перенос существующих данных из старой схемы в новую

-- Шаг 1: Создаем роль 'ROLE_MANAGER', если она еще не существует.
-- ON CONFLICT DO NOTHING делает скрипт безопасным для повторного запуска.
INSERT INTO roles (name, description)
VALUES ('ROLE_MANAGER', 'Manager of enterprises')
ON CONFLICT (name) DO NOTHING;

-- Шаг 2: Переносим аутентификационные данные из старой таблицы 'managers' в новую 'users'.
INSERT INTO users (username, password_digest)
SELECT username, password_digest FROM managers;

-- Шаг 3: Назначаем роль 'ROLE_MANAGER' всем пользователям, которых мы только что создали.
INSERT INTO user_roles (user_id, role_id)
SELECT
    u.id, -- Новый ID пользователя из таблицы 'users'
    (SELECT r.id FROM roles r WHERE r.name = 'ROLE_MANAGER') -- ID роли 'ROLE_MANAGER'
FROM
    users u
WHERE
    u.username IN (SELECT username FROM managers); -- Выбираем только тех пользователей, которые были менеджерами

-- Шаг 4: Создаем профили менеджеров для каждого перенесенного пользователя.
INSERT INTO manager_profiles (user_id)
SELECT
    u.id -- Новый ID пользователя
FROM
    users u
WHERE
    u.username IN (SELECT username FROM managers);

-- Шаг 5: Восстанавливаем связи менеджеров с предприятиями в новой связующей таблице.
INSERT INTO manager_enterprises (manager_profile_id, enterprise_id)
SELECT
    -- Находим новый ID пользователя (который теперь является ID профиля) по старому имени пользователя
    (SELECT u.id FROM users u WHERE u.username = m.username),
    em.enterprise_id -- ID предприятия из старой связующей таблицы
FROM
    enterprise_manager em -- Начинаем со старой связующей таблицы
JOIN
    managers m ON em.manager_id = m.id; -- Присоединяем 'managers', чтобы получить имя пользователя