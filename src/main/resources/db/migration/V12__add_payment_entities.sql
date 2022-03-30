ALTER TABLE payment
    ADD COLUMN date timestamp DEFAULT current_timestamp;

DROP FUNCTION IF EXISTS find_all_payments;

CREATE FUNCTION find_all_payments_eager()
    RETURNS SETOF payment
    LANGUAGE sql
AS
$BODY$
SELECT DISTINCT p.*
FROM payment p
         LEFT OUTER JOIN legal_entity ON p.legal_entity_id = legal_entity.id
         LEFT OUTER JOIN payment_purpose ON p.payment_purpose_id = payment_purpose.id
         LEFT OUTER JOIN bank_branch ON p.bank_branch_id = bank_branch.id
         LEFT OUTER JOIN individual_entity ON p.individual_entity_id = individual_entity.id
ORDER BY p.id;
$BODY$;

/*
 individual:
    1 - Иванов Иван Иванович
    2 - Петр Петров Петрович
    3 - Андреев Андрей Андреевич

 legal:
    1 - Аптека свита
        checking_account:
            1 - 10..01
                payment_purpose:
                    1 - Покупка лекарств
            2 - 10..02
                payment_purpose:
                    2 - Аренда помещения
    2 - Скалбокс
        checking_account:
            3 - 20..01
                payment_purpose:
                     3 - Оплата обучения
                     4 - Оплата стажировки
            4 - 20..02
                payment_purpose:
                    5 - На занавески
 bank_branch:
    1 - Адрес первого филиала
    2 - Адрес второго филиала
    3 - Адрес третьего филиала

 */

INSERT INTO payment(id, individual_entity_id, legal_entity_id, bank_branch_id, payment_purpose_id, date, value)
VALUES (1, 1, 2, 1, 3, current_timestamp, 15000),
       (2, 1, 2, 1, 3, current_timestamp, 35000),
       (3, 1, 1, 2, 1, current_timestamp, 1000),
       (4, 1, 1, 1, 1, current_timestamp, 1777),

       (5, 2, 1, 1, 1, current_timestamp, 4000),
       (6, 2, 2, 1, 3, current_timestamp, 50000);
