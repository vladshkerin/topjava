/* CREATE TABLE MEALS */
DROP TABLE IF EXISTS meals;
DROP SEQUENCE IF EXISTS global_seq_meal;

CREATE SEQUENCE global_seq_meal START WITH 100000;

CREATE TABLE meals
(
    id              INTEGER PRIMARY KEY DEFAULT nextval('global_seq_meal'),
    date_time       TIMESTAMP           DEFAULT now() NOT NULL,
    description     VARCHAR                           NOT NULL,
    calories        INTEGER                           NOT NULL,
    user_id         INTEGER                           NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
);
