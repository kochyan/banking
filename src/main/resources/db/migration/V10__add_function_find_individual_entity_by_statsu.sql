CREATE OR REPLACE FUNCTION find_individual_entity_by_status(IN entity_status varchar(64))
    RETURNS SETOF individual_entity
    LANGUAGE sql
AS
$BODY$
SELECT DISTINCT * FROM individual_entity
    WHERE individual_entity.status = entity_status;
$BODY$