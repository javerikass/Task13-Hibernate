package ru.clevertec.ecl.dto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.ecl.entity.Person;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HouseDto {

    private UUID uuid;

    private String area;

    private String country;

    private String city;

    private String street;

    private String number;

    private LocalDateTime createDate;

    private Set<Person> residents;

    private Set<Person> owners;

}
