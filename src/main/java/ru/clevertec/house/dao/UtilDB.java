package ru.clevertec.house.dao;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class UtilDB {

    /**
     * HOUSE QUERIES
     */
    public static final String SEARCH_HOUSES = "SELECT * FROM clevertec_storage.house " +
        "WHERE LOWER(area) LIKE LOWER(?) OR LOWER(country) LIKE LOWER(?) " +
        "OR LOWER(city) LIKE LOWER(?) OR LOWER(street) LIKE LOWER(?) OR LOWER(number) LIKE LOWER(?)";

    public static final String DELETE_HOUSE = "DELETE FROM House h WHERE h.uuid=:uuid";

    public static final String FIND_OWNED_HOUSES_BY_PERSON =
        "SELECT h.id, h.uuid, h.area, h.country, h.city, h.street, h.number, h.createDate"
            + " FROM House h JOIN h.owners oh where oh.uuid=:uuid";
    public static final String UPDATE_HOUSE = "UPDATE House h "
        + "SET h.number=:number, h.street=:street, "
        + "h.city=:city, h.country=:country, h.area=:area "
        + "WHERE h.uuid=:uuid";

    /**
     * PERSON QUERIES
     */
    public static final String SEARCH_PEOPLE = "SELECT * FROM clevertec_storage.person " +
        "WHERE LOWER(name) LIKE LOWER(?) OR LOWER(surname) LIKE LOWER(?) " +
        "OR LOWER(passport_series) LIKE LOWER(?) OR LOWER(passport_number) LIKE LOWER(?)";

    public static final String FIND_ALL_RESIDENTS_BY_HOUSE = "SELECT p.uuid,p.name,"
        + "p.surname,p.sex,"
        + "p.passportSeries,p.passportNumber,"
        + "p.createDate,p.updateDate"
        + " FROM Person p JOIN p.house h WHERE h.uuid = :uuid";

    public static final String DELETE_PERSON = "DELETE FROM Person p WHERE p.uuid=:uuid";
    public static final String UPDATE_PERSON = "UPDATE Person p "
        + "SET p.name=:name, p.surname=:surname, "
        + "p.sex=:sex,p.passportNumber=:passportNumber,p.passportSeries=:passportSeries "
        + "WHERE p.uuid=:uuid";

    /**
     * OTHER QUERIES
     */

    public static final String UPDATE_FUNCTION =
        "CREATE OR REPLACE FUNCTION clevertec_storage.entity_update_trigger() " +
            "RETURNS TRIGGER AS $$ " +
            "BEGIN " +
            "   IF NEW.name IS DISTINCT FROM OLD.name " +
            "       OR NEW.surname IS DISTINCT FROM OLD.surname " +
            "       OR NEW.passport_number IS DISTINCT FROM OLD.passport_number " +
            "       OR NEW.passport_series IS DISTINCT FROM OLD.passport_series " +
            "       OR NEW.sex IS DISTINCT FROM OLD.sex " +
            "   THEN NEW.update_date = CURRENT_TIMESTAMP; " +
            "   END IF; " +
            "   RETURN NEW; " +
            "END; " +
            "$$ LANGUAGE plpgsql;";

    public static final String CREATE_TRIGGER =
        "CREATE TRIGGER entity_update_trigger " +
            "BEFORE UPDATE ON clevertec_storage.person " +
            "FOR EACH ROW EXECUTE FUNCTION clevertec_storage.entity_update_trigger();";

}
