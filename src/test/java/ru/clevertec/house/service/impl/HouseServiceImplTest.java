package ru.clevertec.house.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.house.dao.HouseDao;
import ru.clevertec.house.dto.request.HouseRequest;
import ru.clevertec.house.dto.response.HouseResponse;
import ru.clevertec.house.entity.House;
import ru.clevertec.house.exception.ResourceNotFoundException;
import ru.clevertec.house.mapper.HouseMapper;
import ru.clevertec.house.testData.HouseTestData;

@ExtendWith(MockitoExtension.class)
class HouseServiceImplTest {

    @Mock
    private HouseDao houseDao;

    @Mock
    private HouseMapper mapper;

    @InjectMocks
    private HouseServiceImpl houseService;

    @Test
    void findByIdShouldReturnHouseResponse() {
        // given
        UUID uuid = HouseTestData.getHouseResponse().getUuid();
        HouseResponse expectedResponse = HouseTestData.getHouseResponse();

        when(houseDao.findById(uuid)).thenReturn(Optional.of(HouseTestData.getHouse()));
        when(mapper.toHouseResponse(any())).thenReturn(expectedResponse);

        // when
        HouseResponse actualResponse = houseService.findById(uuid);

        // then
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void findByIdShouldThrowsResourceNotFoundException() {
        // given
        UUID uuid = HouseTestData.getHouseResponse().getUuid();
        when(houseDao.findById(uuid)).thenReturn(Optional.empty());

        // when and then
        assertThrows(ResourceNotFoundException.class, () -> houseService.findById(uuid));
        verify(houseDao).findById(uuid);
        verify(mapper, never()).toHouseResponse(any());
    }

    @Test
    void saveShouldReturnGeneratedId() {
        // given
        HouseRequest houseRequest = HouseTestData.getHouseRequest();
        long expectedId = 1L;

        when(houseDao.save(any())).thenReturn(expectedId);

        // when
        long actualId = houseService.save(houseRequest);

        // then
        assertEquals(expectedId, actualId);
    }

    @Test
    void updateShouldCallUpdateMethod() {
        // given
        HouseRequest houseRequest = HouseTestData.getHouseRequest();

        // when
        houseService.update(houseRequest);

        // then
        verify(houseDao).update(any());
        verify(mapper).toHouse(houseRequest);
    }

    @Test
    void deleteShouldCallDeleteMethod() {
        // given
        UUID uuid = HouseTestData.getHouseResponse().getUuid();

        // when
        houseService.delete(uuid);

        // then
        verify(houseDao).delete(uuid);
    }

    @Test
    void findAllShouldReturnsListOfHouseResponse() {
        // given
        int page = 1;
        int pageSize = 10;
        List<House> housesList = HouseTestData.getHouseList();
        List<HouseResponse> expectedHouseResponseList = HouseTestData.getHouseResponseList();

        when(houseDao.findAll(page, pageSize)).thenReturn(housesList);
        when(mapper.toListHouseResponse(housesList)).thenReturn(expectedHouseResponseList);

        // when
        List<HouseResponse> actualResponseList = houseService.findAll(page, pageSize);

        // then
        assertEquals(expectedHouseResponseList, actualResponseList);
    }

    @Test
    void findOwnedHousesByPersonShouldReturnsListOfHouseResponse() {
        // given
        UUID personId = HouseTestData.getHouseResponse().getUuid();
        List<House> housesList = HouseTestData.getHouseList();
        List<HouseResponse> expectedHouseResponseList = HouseTestData.getHouseResponseList();

        when(houseDao.findOwnedHousesByPerson(personId)).thenReturn(housesList);
        when(mapper.toListHouseResponse(housesList)).thenReturn(expectedHouseResponseList);

        // when
        List<HouseResponse> actualResponseList = houseService.findOwnedHousesByPerson(personId);

        // then
        assertEquals(expectedHouseResponseList, actualResponseList);
    }

    @Test
    void searchHousesShouldReturnsListOfHouseResponse() {
        // given
        String searchText = "test";
        List<House> housesList = HouseTestData.getHouseList();
        List<HouseResponse> expectedHouseResponseList = HouseTestData.getHouseResponseList();

        when(houseDao.searchHouses(searchText)).thenReturn(housesList);
        when(mapper.toListHouseResponse(housesList)).thenReturn(expectedHouseResponseList);

        // when
        List<HouseResponse> actualResponseList = houseService.searchHouses(searchText);

        // then
        assertEquals(expectedHouseResponseList, actualResponseList);
    }

}