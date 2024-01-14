package ru.clevertec.house.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import ru.clevertec.house.dto.request.HouseRequest;
import ru.clevertec.house.dto.response.HouseResponse;
import ru.clevertec.house.entity.House;

@Mapper(componentModel = "spring")
public interface HouseMapper {

    HouseResponse toHouseResponse(House house);

    House toHouse(HouseRequest houseRequest);

    List<HouseResponse> toListHouseResponse(List<House> houses);

}
