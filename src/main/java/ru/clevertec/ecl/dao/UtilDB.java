package ru.clevertec.ecl.dao;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class UtilDB {

    public static final String DELETE_PERSON = "DELETE FROM clevertec_storage.person WHERE uuid = ?";
    public static final String DELETE_HOUSE = "DELETE FROM clevertec_storage.house WHERE uuid = ?";

}
