CREATE OR REPLACE FUNCTION find_legal_entity_by_status(IN entity_status varchar)
    RETURNS TABLE
            (
                id     int8,
                name   varchar,
                status varchar
            )
    LANGUAGE sql
AS
$BODY$
SELECT DISTINCT le.id, le.name, le.status
FROM legal_entity le
         LEFT OUTER JOIN checking_account ca
                         ON le.id = ca.legal_entity_id AND ca.status = entity_status
         LEFT OUTER JOIN payment_purpose pp
                         ON ca.id = pp.checking_account_id AND pp.status = entity_status
WHERE le.status = entity_status;
$BODY$;