CREATE SCHEMA IF NOT EXISTS clevertec_storage;

INSERT INTO clevertec_storage.house (id, uuid, area, country, city, street, number, create_date)
VALUES
    (1, '2ee52a83-570a-4ca0-a9b6-5a1d715e617d', 'Area 1', 'Country 1', 'City 1', 'Street 1', 'Number 1', CURRENT_TIMESTAMP),
    (2, '0b9842eb-7aa3-43a0-897e-bc4ab77d8231', 'Area 2', 'Country 2', 'City 2', 'Street 2', 'Number 2', CURRENT_TIMESTAMP),
    (3, '5a8eeec2-b66a-44d3-bbd5-3c83b026a918', 'Area 3', 'Country 3', 'City 3', 'Street 3', 'Number 3', CURRENT_TIMESTAMP),
    (4, 'c126450d-fa5c-4004-966d-390aef08cbe8', 'Area 4', 'Country 4', 'City 4', 'Street 4', 'Number 4', CURRENT_TIMESTAMP),
    (5, '8d2db5d1-3c7f-4b60-a282-dc21295eee39', 'Area 5', 'Country 5', 'City 5', 'Street 5', 'Number 5', CURRENT_TIMESTAMP);

INSERT INTO clevertec_storage.person (id, uuid, name, surname, sex, passport_series, passport_number, house_id, create_date, update_date)
VALUES
    (1, 'cf6ff1d2-e2fc-4f60-850c-0179d262e949', 'John', 'Doe', 'MALE', 'AB', '123456', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 'fe5c42e9-72a4-46c0-9377-00683f8bbe10', 'Jane', 'Doe', 'FEMALE', 'CD', '789012', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, '0437bbfe-cd7f-4893-bb1c-daf48512ce5a', 'Alice', 'Johnson', 'FEMALE', 'EF', '345678', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 'b2558226-08bb-4794-ae48-f3ddb6d0aa0b', 'Bob', 'Smith', 'MALE', 'GH', '901234', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, '7af91ae5-9d63-4227-a509-62345dedc3cf', 'Charlie', 'Brown', 'MALE', 'IJ', '567890', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (6, '57684b5c-f002-46ea-9c98-21ff441047e2', 'Diana', 'Miller', 'FEMALE', 'KL', '123987', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (7, 'b3add40a-fe97-4f05-bdb0-d963d26e463b', 'Eva', 'White', 'FEMALE', 'MN', '456789', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (8, '7a0b6fc7-08f0-466a-8001-5b20c8162419', 'Frank', 'Taylor', 'MALE', 'OP', '987654', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (9, '99c0b743-2494-4b92-a920-088b2ffde01c', 'Grace', 'Anderson', 'FEMALE', 'QR', '321098', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (10, '2070ff72-d5d7-4063-878a-db47cc2c2788', 'Henry', 'Clark', 'MALE', 'ST', '654321', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO clevertec_storage.owner_house (owner_id, house_id)
VALUES (1, 1),
       (2, 1),
       (3, 2),
       (4, 2),
       (5, 3),
       (6, 3),
       (7, 4),
       (8, 4),
       (9, 5),
       (10, 5);

CREATE OR REPLACE FUNCTION clevertec_storage.entity_update_trigger() RETURNS TRIGGER AS $$
BEGIN
    IF NEW.name IS DISTINCT FROM OLD.name
        OR NEW.surname IS DISTINCT FROM OLD.surname
        OR NEW.passport_number IS DISTINCT FROM OLD.passport_number
        OR NEW.passport_series IS DISTINCT FROM OLD.passport_series
        OR NEW.sex IS DISTINCT FROM OLD.sex
    THEN NEW.update_date = CURRENT_TIMESTAMP; END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER entity_update_trigger
    BEFORE UPDATE ON clevertec_storage.person
    FOR EACH ROW
EXECUTE FUNCTION clevertec_storage.entity_update_trigger();