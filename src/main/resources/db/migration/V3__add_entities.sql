CREATE TABLE IF NOT EXISTS bank_branch
(
    id      int8         NOT NULL UNIQUE,
    address varchar(120) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS legal_entity
(
    id     int8        NOT NULL UNIQUE,
    name   varchar(64) NOT NULL,
    status varchar(64) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS individual_entity
(
    id         int8        NOT NULL UNIQUE,
    firstname  varchar(64) NOT NULL,
    lastname   varchar(64) NOT NULL,
    patronymic varchar(64) NOT NULL,
    status     varchar(64) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS checking_account
(
    id              int8        NOT NULL UNIQUE,
    legal_entity_id int8        NOT NULL,
    value           varchar(32) NOT NULL,
    status          varchar(64) NOT NULL,
    FOREIGN KEY (legal_entity_id) REFERENCES legal_entity (id),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS payment_purpose
(
    id                  int8        NOT NULL UNIQUE,
    checking_account_id int8        NOT NULL,
    name                varchar(64) NOT NULL,
    status              varchar(64) NOT NULL,
    FOREIGN KEY (checking_account_id) REFERENCES checking_account (id),
    PRIMARY KEY (id)
);

INSERT INTO bank_branch(id, address)
VALUES (1, 'Адресс первого филиала'),
       (2, 'Адресс второго филиала'),
       (3, 'Адресс третьего филиала');

INSERT INTO individual_entity(id, firstname, lastname, patronymic, status)
VALUES (1, 'Иван', 'Иванов', 'Иванович', 'ACTIVE'),
       (2, 'Петр', 'Петров', 'Петрович', 'ACTIVE'),
       (3, 'Андрей', 'Андреев', 'Андреевич', 'ACTIVE');

INSERT INTO legal_entity(id, name, status)
VALUES (1, 'АПТЕКА СВИТА', 'ACTIVE'),
       (2, 'СКАЛБОКС', 'ACTIVE');

INSERT INTO checking_account(id, legal_entity_id, value, status)
VALUES (1, 1, '100001', 'ACTIVE'),
       (2, 1, '100002', 'ACTIVE'),

       (3, 2, '200001', 'ACTIVE'),
       (4, 2, '200002', 'ACTIVE');

INSERT INTO payment_purpose(id, checking_account_id, name, status)
VALUES (1, 1, 'ПОКУПКА ЛЕКАРСТВ', 'ACTIVE'),
       (2, 2, 'АРЕНДА ПОМЕЩЕНИЯ', 'ACTIVE'),

       (3, 3, 'ОПЛАТА ОБУЧЕНИЯ', 'ACTIVE'),
       (4, 3, 'ОПЛАТА СТАЖИРОВКИ', 'ACTIVE'),

       (5, 4, 'НА ЗАНАВЕСКИ', 'ACTIVE');