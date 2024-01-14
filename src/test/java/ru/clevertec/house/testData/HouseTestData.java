package ru.clevertec.house.testData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import ru.clevertec.house.dto.request.HouseRequest;
import ru.clevertec.house.dto.response.HouseResponse;
import ru.clevertec.house.entity.House;

@Data
@Builder(setterPrefix = "with")
public class HouseTestData {

    @Builder.Default
    private static UUID uuid = UUID.fromString("869bda25-e7aa-49d9-8b76-958c6b9f1600");
    @Builder.Default
    private static String area = "Area";
    @Builder.Default
    private static String country = "Country";
    @Builder.Default
    private static String city = "City";
    @Builder.Default
    private static String street = "Street";
    @Builder.Default
    private static String number = "Number";
    @Builder.Default
    private static LocalDateTime createDate = LocalDateTime.now();

    public static HouseRequest getHouseRequest() {
        return HouseRequest.builder()
            .uuid(uuid)
            .area(area)
            .country(country)
            .city(city)
            .street(street)
            .number(number)
            .build();
    }

    public static HouseResponse getHouseResponse() {
        return HouseResponse.builder()
            .uuid(uuid)
            .area(area)
            .country(country)
            .city(city)
            .street(street)
            .number(number)
            .createDate(createDate)
            .build();
    }

    public static House getHouse() {
        return House.builder().
            uuid(uuid)
            .area(area)
            .country(country)
            .city(city)
            .street(street)
            .number(number)
            .createDate(createDate)
            .build();
    }

    public static List<House> getHouseList() {
        List<House> housesList = new ArrayList<>();
        housesList.add(HouseTestData.getHouse());
        return housesList;
    }

    public static List<HouseResponse> getHouseResponseList() {
        List<HouseResponse> houseResponseList = new ArrayList<>();
        houseResponseList.add(HouseTestData.getHouseResponse());
        return houseResponseList;
    }

}
