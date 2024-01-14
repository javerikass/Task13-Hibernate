package ru.clevertec.house.service.impl;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.house.dao.HouseDao;
import ru.clevertec.house.dao.PersonDao;
import ru.clevertec.house.dto.request.PersonRequest;
import ru.clevertec.house.dto.request.PersonStatus;
import ru.clevertec.house.dto.response.PersonResponse;
import ru.clevertec.house.entity.House;
import ru.clevertec.house.entity.Person;
import ru.clevertec.house.exception.ResourceNotFoundException;
import ru.clevertec.house.mapper.PersonMapper;
import ru.clevertec.house.service.PersonService;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonDao personDao;
    private final HouseDao houseDao;
    private final PersonMapper mapper;

    @Override
    public PersonResponse findById(UUID uuid) {
        return mapper.toPersonResponse(personDao.findById(uuid)
            .orElseThrow(() -> new ResourceNotFoundException(uuid)));
    }

    @Override
    public long save(PersonRequest personRequest) {
        Person person = mapper.toPerson(personRequest);
        House house = houseDao.findById(personRequest.getHouseUuid()).orElseThrow();

        if (personRequest.getStatus().equals(PersonStatus.RESIDENT)) {
            house.addResident(person);
        } else {
            person.addOwnership(house);
        }

        return personDao.save(person);
    }

    @Override
    public void update(PersonRequest personRequest) {
        personDao.update(mapper.toPerson(personRequest));
    }

    @Override
    public void delete(UUID uuid) {
        personDao.delete(uuid);
    }

    @Override
    public List<PersonResponse> findAll(int page, int pageSize) {
        return mapper.toListPersonResponse(personDao.findAll(page, pageSize));
    }

    @Override
    public List<PersonResponse> findAllResidentsByHouse(UUID uuid) {
        return mapper.toListPersonResponse(personDao.findAllResidentsByHouse(uuid));
    }

    @Override
    public List<PersonResponse> searchPersons(String text) {
        return mapper.toListPersonResponse(personDao.searchPersons(text));
    }

}
