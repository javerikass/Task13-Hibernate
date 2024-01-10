package ru.clevertec.ecl.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clevertec.ecl.dto.PersonDto;
import ru.clevertec.ecl.entity.Person;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonDto toPersonDto(Person person);

    Person toPerson(PersonDto personDto);

    List<PersonDto> toListPersonDto(List<Person> people);

}
