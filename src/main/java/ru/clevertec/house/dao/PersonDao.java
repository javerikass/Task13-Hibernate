package ru.clevertec.house.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import ru.clevertec.house.entity.Person;

public interface PersonDao {

    Optional<Person> findById(UUID uuid);

    long save(Person person);

    void update(Person person);

    void delete(UUID uuid);

    List<Person> findAll(int page, int pageSize);

    List<Person> findAllResidentsByHouse(UUID uuid);

    List<Person> searchPersons(String text);

}
