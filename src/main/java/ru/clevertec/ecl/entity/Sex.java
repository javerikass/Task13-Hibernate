package ru.clevertec.ecl.entity;

public enum Sex {
    MALE("Male"),
    FEMALE("Female");

    private String name;

    Sex(String name) {
        this.name = name;
    }
}
