package ru.clevertec.house.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import ru.clevertec.house.dto.request.PersonRequest;
import ru.clevertec.house.dto.response.PersonResponse;
import ru.clevertec.house.entity.Person;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonResponse toPersonResponse(Person person);

    Person toPerson(PersonRequest personRequest);

    List<PersonResponse> toListPersonResponse(List<Person> people);

}
