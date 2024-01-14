package ru.clevertec.house.dto.request;

import lombok.Getter;

@Getter
public enum PersonStatus {

    RESIDENT("Resident"),
    OWNER("Owner");

    private final String name;

    PersonStatus(String name) {
        this.name = name;
    }

}
