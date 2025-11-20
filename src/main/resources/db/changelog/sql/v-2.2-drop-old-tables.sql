-- V2.2: Удаление устаревших таблиц 'managers' и 'enterprise_manager'

-- Удаляем старую связующую таблицу
DROP TABLE enterprise_manager;

-- Удаляем старую таблицу менеджеров
DROP TABLE managers;