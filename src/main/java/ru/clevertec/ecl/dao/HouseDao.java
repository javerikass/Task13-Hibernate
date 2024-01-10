package ru.clevertec.ecl.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import ru.clevertec.ecl.entity.House;

public interface HouseDao {

    Optional<House> findById(long id);

    long save(House house);

    void update(House house);

    void delete(UUID uuid);

    List<House> findAll();

}
