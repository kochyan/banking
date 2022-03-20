CREATE OR REPLACE FUNCTION cascade_update_legal_entity_status() RETURNS TRIGGER
AS
$BODY$
BEGIN
    IF TG_OP = 'UPDATE' THEN
        IF OLD.status != NEW.status THEN
            UPDATE checking_account
            SET status = NEW.status
            WHERE checking_account.legal_entity_id = NEW.id;
        END IF;
    END IF;
    RETURN NEW;
END;
$BODY$ LANGUAGE plpgsql;

CREATE TRIGGER tg_update_status_cascade
    AFTER UPDATE
    ON legal_entity
    FOR EACH ROW
EXECUTE PROCEDURE cascade_update_legal_entity_status();

CREATE OR REPLACE FUNCTION cascade_update_checking_account_status() RETURNS TRIGGER
AS
$BODY$
BEGIN
    IF TG_OP = 'UPDATE' THEN
        IF OLD.status != NEW.status THEN
            UPDATE payment_purpose
            SET status = NEW.status
            WHERE payment_purpose.checking_account_id = NEW.id;
        END IF;
    END IF;
    RETURN NEW;
END;
$BODY$ LANGUAGE plpgsql;

CREATE TRIGGER tg_update_status_cascade
    AFTER UPDATE
    ON checking_account
    FOR EACH ROW
EXECUTE PROCEDURE cascade_update_checking_account_status();