package ru.clevertec.house.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
import ru.clevertec.house.dto.request.PersonRequest;
import ru.clevertec.house.dto.response.HouseResponse;
import ru.clevertec.house.dto.response.PersonResponse;
import ru.clevertec.house.service.HouseService;
import ru.clevertec.house.service.PersonService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;
    private final HouseService houseService;

    @PostMapping("/save")
    public ResponseEntity<Long> createPerson(@RequestBody @Valid PersonRequest personRequest) {
        long personId = personService.save(personRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(personId);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<PersonResponse> getPersonById(@PathVariable("uuid") UUID uuid) {
        return ResponseEntity.ok(personService.findById(uuid));
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePerson(@RequestBody @Valid PersonRequest personRequest) {
        personService.update(personRequest);
    }

    @DeleteMapping("/delete/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable("uuid") UUID uuid) {
        personService.delete(uuid);
    }

    @GetMapping
    public ResponseEntity<List<PersonResponse>> getAllPersons(
        @RequestParam(defaultValue = "1", name = "page") int page,
        @RequestParam(defaultValue = "15", name = "pageSize") int pageSize) {
        return ResponseEntity.ok(personService.findAll(page, pageSize));
    }

    @GetMapping("/owner/{personId}")
    public ResponseEntity<List<HouseResponse>> getOwnedHouses(
        @PathVariable("personId") UUID personId) {
        return ResponseEntity.ok(houseService.findOwnedHousesByPerson(personId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<PersonResponse>> getPeopleBySearchingByText(
        @RequestParam(name = "text") String text) {
        return ResponseEntity.ok(personService.searchPersons(text));
    }

}
