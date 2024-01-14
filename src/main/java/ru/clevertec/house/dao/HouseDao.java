package ru.clevertec.house.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import ru.clevertec.house.entity.House;

public interface HouseDao {

    Optional<House> findById(UUID uuid);

    long save(House house);

    void update(House house);

    void delete(UUID uuid);

    List<House> findAll(int page, int pageSize);

    List<House> findOwnedHousesByPerson(UUID uuid);

    List<House> searchHouses(String text);

}
