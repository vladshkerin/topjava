/* INSERT DATA IN MEALS */
DELETE FROM meals;

ALTER SEQUENCE global_seq_meal RESTART WITH 100000;

INSERT INTO meals (description, calories, user_id)
VALUES ('Пользователь ланч', 430, 100000),
       ('Пользователь ужин', 1400, 100000),
       ('Админ ланч', 510, 100001),
       ('Админ ужин', 1500, 100001);
