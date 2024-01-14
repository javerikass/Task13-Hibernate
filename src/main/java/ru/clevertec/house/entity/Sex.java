package ru.clevertec.house.entity;

import lombok.Getter;

@Getter
public enum Sex {

    MALE("Male"),
    FEMALE("Female");

    private final String name;

    Sex(String name) {
        this.name = name;
    }
}
