package ru.clevertec.house.testData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import ru.clevertec.house.dto.request.PersonRequest;
import ru.clevertec.house.dto.request.PersonStatus;
import ru.clevertec.house.dto.response.PersonResponse;
import ru.clevertec.house.entity.Person;
import ru.clevertec.house.entity.Sex;

@Data
@Builder(setterPrefix = "with")
public class PersonTestData {

    @Builder.Default
    private static UUID uuid = UUID.fromString("869bda25-e7aa-49d9-8b76-958c6b9f1600");
    @Builder.Default
    private static String name = "John";
    @Builder.Default
    private static String surname = "Doe";
    @Builder.Default
    private static Sex sex = Sex.MALE;
    @Builder.Default
    private static String passportSeries = "AB";
    @Builder.Default
    private static String passportNumber = "1234567";
    @Builder.Default
    private static LocalDateTime createDate = LocalDateTime.now();
    @Builder.Default
    private static LocalDateTime updateDate = LocalDateTime.now();

    public static PersonRequest getPersonRequest() {
        return PersonRequest.builder()
            .uuid(uuid)
            .name(name)
            .surname(surname)
            .sex(sex)
            .passportSeries(passportSeries)
            .passportNumber(passportNumber)
            .houseUuid(UUID.randomUUID())
            .status(PersonStatus.RESIDENT)
            .build();
    }

    public static PersonResponse getPersonResponse() {
        return PersonResponse.builder()
            .uuid(uuid)
            .name(name)
            .surname(surname)
            .sex(sex)
            .passportSeries(passportSeries)
            .passportNumber(passportNumber)
            .createDate(createDate)
            .updateDate(updateDate)
            .build();
    }

    public static Person getPerson() {
        return Person.builder()
            .uuid(uuid)
            .name(name)
            .surname(surname)
            .sex(sex)
            .passportSeries(passportSeries)
            .passportNumber(passportNumber)
            .createDate(createDate)
            .updateDate(updateDate)
            .build();
    }

    public static List<Person> getPersonList() {
        List<Person> personsList = new ArrayList<>();
        personsList.add(PersonTestData.getPerson());
        return personsList;
    }

    public static List<PersonResponse> getPersonResponseList() {
        List<PersonResponse> personResponseList = new ArrayList<>();
        personResponseList.add(PersonTestData.getPersonResponse());
        return personResponseList;
    }
}
