package ru.clevertec.ecl.service;

import java.util.List;
import java.util.UUID;
import ru.clevertec.ecl.dto.HouseDto;

public interface HouseService {

    long save(HouseDto houseDto);

    HouseDto findById(long id);

    void update(HouseDto houseDto);

    void delete(UUID uuid);

    List<HouseDto> findAll();

}
