package ru.clevertec.ecl.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.ecl.entity.House;
import ru.clevertec.ecl.entity.Sex;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDto {

    private UUID uuid;

    private String name;

    private String surname;

    private Sex sex;

    private String passportSeries;

    private String passportNumber;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    private House house;

    private Set<House> houses;

}
