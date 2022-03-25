CREATE TABLE IF NOT EXISTS payment
(
    id                   int8             NOT NULL UNIQUE,
    individual_entity_id int8             NOT NULL,
    legal_entity_id      int8             NOT NULL,
    bank_branch_id       int8             NOT NULL,
    payment_purpose_id   int8             NOT NULL,
    value                double precision NOT NULL,
    FOREIGN KEY (individual_entity_id) REFERENCES individual_entity (id),
    FOREIGN KEY (legal_entity_id) REFERENCES legal_entity (id),
    FOREIGN KEY (payment_purpose_id) REFERENCES payment_purpose (id),
    FOREIGN KEY (bank_branch_id) REFERENCES bank_branch (id),
    PRIMARY KEY (id)
);

CREATE FUNCTION find_all_payments()
    RETURNS SETOF payment
    LANGUAGE sql
AS
$BODY$
    SELECT DISTINCT * FROM payment;
$BODY$;

CREATE FUNCTION find_all_payments_by_legal(IN legal_id int8)
    RETURNS SETOF payment
    LANGUAGE sql
AS
$BODY$
    SELECT DISTINCT * FROM payment
        WHERE payment.legal_entity_id = legal_id;
$BODY$;

CREATE FUNCTION find_all_payments_by_individual(IN individual_id int8)
    RETURNS SETOF payment
    LANGUAGE sql
AS
$BODY$
    SELECT DISTINCT * FROM payment
        WHERE payment.individual_entity_id = individual_id;
$BODY$;