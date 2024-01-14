package ru.clevertec.house.service;

import java.util.List;
import java.util.UUID;
import ru.clevertec.house.dto.request.PersonRequest;
import ru.clevertec.house.dto.response.PersonResponse;

public interface PersonService {

    PersonResponse findById(UUID uuid);

    long save(PersonRequest personRequest);

    void update(PersonRequest personRequest);

    void delete(UUID uuid);

    List<PersonResponse> findAll(int page, int pageSize);

    List<PersonResponse> findAllResidentsByHouse(UUID uuid);

    List<PersonResponse> searchPersons(String text);

}
