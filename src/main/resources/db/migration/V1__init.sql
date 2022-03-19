CREATE TABLE IF NOT EXISTS hibernate_sequence
(
    next_val int8
);

INSERT INTO hibernate_sequence
VALUES (50);

CREATE TABLE IF NOT EXISTS usr
(
    id        int8        NOT NULL UNIQUE,
    username  varchar(64) NOT NULL UNIQUE,
    password  varchar(64) NOT NULL,
    firstname varchar(64) NOT NULL,
    lastname  varchar(64) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS role
(
    id   int8        NOT NULL UNIQUE,
    name varchar(64) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE privilege
(
    id   int8        NOT NULL,
    name varchar(64) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS usr_role
(
    usr_id  int8 NOT NULL,
    role_id int8 NOT NULL,
    FOREIGN KEY (usr_id) REFERENCES usr (id),
    FOREIGN KEY (role_id) REFERENCES role (id),
    PRIMARY KEY (usr_id, role_id)
);

CREATE TABLE IF NOT EXISTS role_privilege
(
    privilege_id int8 NOT NULL,
    role_id      int8 NOT NULL,
    FOREIGN KEY (privilege_id) REFERENCES privilege (id),
    FOREIGN KEY (role_id) REFERENCES role (id),
    PRIMARY KEY (privilege_id, role_id)
);

INSERT INTO usr(id, username, password, firstname, lastname)
VALUES (1, 'user', 'user', 'Иван', 'Говнов'),
       (2, 'admin', 'admin', 'Вася', 'Пупкин');

INSERT INTO role(id, name)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN');

INSERT INTO privilege(id, name)
VALUES (1, 'CAN_INSERT'),
       (2, 'CAN_UPDATE'),
       (3, 'CAN_DELETE'),
       (4, 'CAN_SELECT'),
       (5, 'CAN_USE_ADMIN_PANEL');

INSERT INTO usr_role (usr_id, role_id)
VALUES (1, 1),
       (2, 2);

INSERT INTO role_privilege(role_id, privilege_id)
VALUES (1, 2),
       (1, 4),

       (2, 1),
       (2, 2),
       (2, 3),
       (2, 4),
       (2, 5);