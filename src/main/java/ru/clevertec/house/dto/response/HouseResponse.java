package ru.clevertec.house.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.house.entity.Person;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonAppend
public class HouseResponse {

    private UUID uuid;

    private String area;

    private String country;

    private String city;

    private String street;

    private String number;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createDate;

    @JsonIgnore
    private Set<Person> residents;
    @JsonIgnore
    private Set<Person> owners;

}
