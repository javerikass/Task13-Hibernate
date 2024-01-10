package ru.clevertec.ecl.service.impl;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dao.PersonDao;
import ru.clevertec.ecl.dto.PersonDto;
import ru.clevertec.ecl.mapper.PersonMapper;
import ru.clevertec.ecl.service.PersonService;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonDao personDao;
    private final PersonMapper mapper;

    @Override
    public PersonDto findById(long id) {
        return mapper.toPersonDto(personDao.findById(id).orElseThrow());
    }

    @Override
    public long save(PersonDto personDto) {
        return personDao.save(mapper.toPerson(personDto));
    }

    @Override
    public void update(PersonDto personDto) {
        personDao.update(mapper.toPerson(personDto));
    }

    @Override
    public void delete(UUID uuid) {
        personDao.delete(uuid);
    }

    @Override
    public List<PersonDto> findAll() {
        return mapper.toListPersonDto(personDao.findAll());
    }

}
