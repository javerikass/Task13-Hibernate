package ru.clevertec.house.exception;

import java.util.UUID;
import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException{

    private final UUID uuid;

    public ResourceNotFoundException(UUID uuid) {
        super("Resource not found with uuid: " + uuid);
        this.uuid = uuid;
    }

}
