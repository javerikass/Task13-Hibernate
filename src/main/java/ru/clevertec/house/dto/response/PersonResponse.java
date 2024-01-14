package ru.clevertec.house.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.house.entity.House;
import ru.clevertec.house.entity.Sex;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonResponse {

    private UUID uuid;

    private String name;

    private String surname;

    private Sex sex;

    private String passportSeries;

    private String passportNumber;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    @JsonIgnore
    private House house;
    @JsonIgnore
    private Set<House> houses;

}
