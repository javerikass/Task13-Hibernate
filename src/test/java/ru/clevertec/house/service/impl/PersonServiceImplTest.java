package ru.clevertec.house.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.house.dao.HouseDao;
import ru.clevertec.house.dao.PersonDao;
import ru.clevertec.house.dto.request.PersonRequest;
import ru.clevertec.house.dto.request.PersonStatus;
import ru.clevertec.house.dto.response.PersonResponse;
import ru.clevertec.house.entity.House;
import ru.clevertec.house.entity.Person;
import ru.clevertec.house.exception.ResourceNotFoundException;
import ru.clevertec.house.mapper.PersonMapper;
import ru.clevertec.house.testData.HouseTestData;
import ru.clevertec.house.testData.PersonTestData;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock
    private PersonDao personDao;

    @Mock
    private HouseDao houseDao;

    @Mock
    private PersonMapper mapper;

    @InjectMocks
    private PersonServiceImpl personService;

    @Test
    void findByIdShouldReturnPersonResponse() {
        // given
        UUID uuid = PersonTestData.getPersonResponse().getUuid();
        PersonResponse expectedResponse = PersonTestData.getPersonResponse();

        when(personDao.findById(uuid)).thenReturn(Optional.of(PersonTestData.getPerson()));
        when(mapper.toPersonResponse(any())).thenReturn(expectedResponse);

        // when
        PersonResponse actualResponse = personService.findById(uuid);

        // then
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void findByIdShouldThrowsResourceNotFoundException() {
        // given
        UUID uuid = PersonTestData.getPersonResponse().getUuid();
        when(personDao.findById(uuid)).thenReturn(Optional.empty());

        // when and then
        assertThrows(ResourceNotFoundException.class, () -> personService.findById(uuid));
        verify(personDao).findById(uuid);
        verify(mapper, never()).toPersonResponse(any());
    }

    @Test
    void saveShouldReturnGeneratedId() {
        // given
        PersonRequest personRequest = PersonTestData.getPersonRequest();
        House house = HouseTestData.getHouse();
        Person person = PersonTestData.getPerson();
        long expectedId = 1L;

        when(mapper.toPerson(personRequest)).thenReturn(person);
        when(houseDao.findById(personRequest.getHouseUuid())).thenReturn(Optional.of(house));
        when(personDao.save(person)).thenReturn(expectedId);

        // when
        long actualId = personService.save(personRequest);

        // then
        assertEquals(expectedId, actualId);
    }


    @Test
    void saveShouldAddPersonAsResidentsOrHouseAsOwnership() {
        // given
        PersonRequest personRequest = PersonTestData.getPersonRequest();
        House house = HouseTestData.getHouse();
        Person person = PersonTestData.getPerson();
        long id = 1L;

        when(mapper.toPerson(personRequest)).thenReturn(person);
        when(houseDao.findById(personRequest.getHouseUuid())).thenReturn(Optional.of(house));
        when(personDao.save(person)).thenReturn(id);

        // when
        personService.save(personRequest);

        // then
        if (PersonStatus.RESIDENT.equals(personRequest.getStatus())) {
            assertTrue(house.getResidents().contains(person));
        } else {
            assertTrue(person.getOwnership().contains(house));
        }
    }

    @Test
    void updateShouldCallUpdateMethod() {
        // given
        PersonRequest personRequest = PersonTestData.getPersonRequest();

        // when
        personService.update(personRequest);

        // then
        verify(personDao).update(any());
        verify(mapper).toPerson(personRequest);
    }

    @Test
    void deleteShouldCallDeleteMethod() {
        // given
        UUID uuid = PersonTestData.getPersonResponse().getUuid();

        // when
        personService.delete(uuid);

        // then
        verify(personDao).delete(uuid);
    }

    @Test
    void findAllShouldReturnsListOfPersonResponse() {
        // given
        int page = 1;
        int pageSize = 10;
        List<Person> personsList = PersonTestData.getPersonList();
        List<PersonResponse> expectedPersonResponseList = PersonTestData.getPersonResponseList();

        when(personDao.findAll(page, pageSize)).thenReturn(personsList);
        when(mapper.toListPersonResponse(personsList)).thenReturn(expectedPersonResponseList);

        // when
        List<PersonResponse> actualResponseList = personService.findAll(page, pageSize);

        // then
        assertEquals(expectedPersonResponseList, actualResponseList);
    }

    @Test
    void findAllResidentsByHouseShouldReturnsListOfPersonResponse() {
        // given
        UUID houseId = PersonTestData.getPersonResponse().getUuid();
        List<Person> personsList = PersonTestData.getPersonList();
        List<PersonResponse> expectedPersonResponseList = PersonTestData.getPersonResponseList();

        when(personDao.findAllResidentsByHouse(houseId)).thenReturn(personsList);
        when(mapper.toListPersonResponse(personsList)).thenReturn(expectedPersonResponseList);

        // when
        List<PersonResponse> actualResponseList = personService.findAllResidentsByHouse(houseId);

        // then
        assertEquals(expectedPersonResponseList, actualResponseList);
    }

    @Test
    void searchPersonsShouldReturnsListOfPersonResponse() {
        // given
        String searchText = "test";
        List<Person> personsList = PersonTestData.getPersonList();
        List<PersonResponse> expectedPersonResponseList = PersonTestData.getPersonResponseList();

        when(personDao.searchPersons(searchText)).thenReturn(personsList);
        when(mapper.toListPersonResponse(personsList)).thenReturn(expectedPersonResponseList);

        // when
        List<PersonResponse> actualResponseList = personService.searchPersons(searchText);

        // then
        assertEquals(expectedPersonResponseList, actualResponseList);
    }

}