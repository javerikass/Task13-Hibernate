package ru.clevertec.house.service;

import java.util.List;
import java.util.UUID;
import ru.clevertec.house.dto.request.HouseRequest;
import ru.clevertec.house.dto.response.HouseResponse;

public interface HouseService {

    long save(HouseRequest houseRequest);

    HouseResponse findById(UUID uuid);

    void update(HouseRequest houseRequest);

    void delete(UUID uuid);

    List<HouseResponse> findAll(int page, int pageSize);

    List<HouseResponse> findOwnedHousesByPerson(UUID uuid);

    List<HouseResponse> searchHouses(String text);

}
