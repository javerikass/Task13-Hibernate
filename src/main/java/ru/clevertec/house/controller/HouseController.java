package ru.clevertec.house.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.house.dto.request.HouseRequest;
import ru.clevertec.house.dto.response.HouseResponse;
import ru.clevertec.house.dto.response.PersonResponse;
import ru.clevertec.house.service.HouseService;
import ru.clevertec.house.service.PersonService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/house")
public class HouseController {

    private final HouseService houseService;
    private final PersonService personService;

    @PostMapping("/save")
    public ResponseEntity<Long> createHouse(@RequestBody @Valid HouseRequest houseRequest) {
        long houseId = houseService.save(houseRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(houseId);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<HouseResponse> getHouseById(@PathVariable("uuid") UUID uuid) {
        return ResponseEntity.ok(houseService.findById(uuid));
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateHouse(@RequestBody HouseRequest houseRequest) {
        houseService.update(houseRequest);
    }

    @DeleteMapping("/delete/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHouse(@PathVariable("uuid") UUID uuid) {
        houseService.delete(uuid);
    }

    @GetMapping
    public ResponseEntity<List<HouseResponse>> getAllHouses(
        @RequestParam(defaultValue = "1", required = false, name = "page") int page,
        @RequestParam(defaultValue = "15", required = false, name = "pageSize") int pageSize) {
        List<HouseResponse> houses = houseService.findAll(page, pageSize);
        return ResponseEntity.ok(houses);
    }

    @GetMapping("/residents/{houseId}")
    public ResponseEntity<List<PersonResponse>> getResidents(
        @PathVariable("houseId") UUID houseId) {
        return ResponseEntity.ok(personService.findAllResidentsByHouse(houseId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<HouseResponse>> getHousesBySearchingByText(
        @RequestParam(name = "text") String text) {
        return ResponseEntity.ok(houseService.searchHouses(text));
    }

}
