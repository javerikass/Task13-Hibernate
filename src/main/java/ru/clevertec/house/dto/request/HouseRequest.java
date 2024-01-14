package ru.clevertec.house.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HouseRequest {

    private UUID uuid;
    @NotNull
    @Size(min = 2, max = 50)
    private String area;
    @NotNull
    @Size(min = 2, max = 50)
    private String country;
    @NotNull
    @Size(min = 2, max = 50)
    private String city;
    @NotNull
    @Size(min = 2, max = 50)
    private String street;
    @NotNull
    @Positive
    private String number;

}
