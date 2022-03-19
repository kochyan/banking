CREATE OR REPLACE FUNCTION find_user_by_credentials(
    IN usr_username varchar(64),
    IN usr_password varchar(64)
)
    RETURNS TABLE
            (
                id        int8,
                username  varchar,
                password  varchar,
                firstname varchar,
                lastname  varchar
            )
    LANGUAGE sql
AS
$BODY$
    SELECT * FROM usr u
    WHERE u.username = usr_username AND u.password = usr_password;
$BODY$;