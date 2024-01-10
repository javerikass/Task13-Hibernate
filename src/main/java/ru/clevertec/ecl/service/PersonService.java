package ru.clevertec.ecl.service;

import java.util.List;
import java.util.UUID;
import ru.clevertec.ecl.dto.PersonDto;

public interface PersonService {

    PersonDto findById(long id);

    long save(PersonDto personDto);

    void update(PersonDto personDto);

    void delete(UUID uuid);

    List<PersonDto> findAll();

}
