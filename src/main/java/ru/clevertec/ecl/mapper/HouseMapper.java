package ru.clevertec.ecl.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clevertec.ecl.dto.HouseDto;
import ru.clevertec.ecl.entity.House;

@Mapper(componentModel = "spring")
public interface HouseMapper {

    HouseDto toHouseDto(House house);

    House toHouse(HouseDto houseDto);

    List<HouseDto> toListHouseDto(List<House> houses);

}
