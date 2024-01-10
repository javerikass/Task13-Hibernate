package ru.clevertec.ecl.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import ru.clevertec.ecl.entity.Person;

public interface PersonDao {

    Optional<Person> findById(long id);

    long save(Person person);

    void update(Person person);

    void delete(UUID uuid);

    List<Person> findAll();

}
