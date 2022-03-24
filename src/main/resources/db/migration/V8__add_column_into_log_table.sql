ALTER TABLE log
    ADD COLUMN IF NOT EXISTS level varchar(64);

ALTER TABLE log
    DROP CONSTRAINT IF EXISTS record_usr_id_fkey;

ALTER TABLE log
    ADD CONSTRAINT log_usr_id_fkey
        FOREIGN KEY (usr_id) REFERENCES usr (id);

ALTER TABLE log
    DROP CONSTRAINT IF EXISTS record_pkey;

ALTER TABLE log
    ADD CONSTRAINT log_pkey
        PRIMARY KEY (id);

ALTER TABLE log
    ALTER COLUMN usr_id DROP NOT NULL;

ALTER TABLE log
    ALTER COLUMN created SET NOT NULL;

DROP TABLE IF EXISTS hibernate_sequence;

CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START 50 INCREMENT 1;