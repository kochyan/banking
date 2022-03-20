CREATE TABLE IF NOT EXISTS record
(
    id      int8         NOT NULL UNIQUE,
    usr_id  int8         NOT NULL,
    message varchar(120) NOT NULL,
    created timestamp DEFAULT current_timestamp,
    FOREIGN KEY (usr_id) REFERENCES usr (id),
    PRIMARY KEY (id)
);