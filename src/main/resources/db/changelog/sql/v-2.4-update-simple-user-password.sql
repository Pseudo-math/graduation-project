-- v-v-2.4-update-simple-user-password.sql
-- Обновляет пароль для пользователя 'simple_user'

UPDATE users
SET password_digest = '$2y$12$m4R525uamjrQQP18hJ/KL.1p1N.pXqJZpmxAp8JlNgNFlUk5vxzqy' -- Хэш для 'new_password'
WHERE username = 'simple_user';