package ru.clevertec.house.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.house.entity.Sex;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonRequest {

    private UUID uuid;
    @NotNull
    @Size(min = 2, max = 50)
    private String name;
    @NotNull
    @Size(min = 2, max = 50)
    private String surname;
    @NotNull
    private Sex sex;
    @NotNull
    @Size(min = 2, max = 2)
    private String passportSeries;
    @NotNull
    @Size(min = 7, max = 7)
    private String passportNumber;
    @NotNull
    private UUID houseUuid;
    @NotNull
    private PersonStatus status;

}
