package com.sofkau.library.controller;

import com.sofkau.library.dto.ResourceDto;
import com.sofkau.library.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/resources")
public class ResourceController {
    @Autowired
    private ResourceService service;

    @GetMapping("/{id}")
    public ResponseEntity<ResourceDto> findById(@PathVariable("id") String id) {
        try {
            return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<ResourceDto>> getAll() {
        try {
            return new ResponseEntity<>(service.getAll(), HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<ResourceDto> create(@Valid @RequestBody ResourceDto dto) {
        try {
            return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<ResourceDto> update(@Valid @RequestBody ResourceDto dto) {
        try {
            if(dto.getId() == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(service.update(dto), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        try {
            service.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/available/{id}")
    public ResponseEntity<String> isAvailableResource(@PathVariable String id) {
        try {
            ResourceDto resource = service.getById(id);

            if(Boolean.FALSE.equals(resource.getAvailable())) {
                return new ResponseEntity<>(
                        "Resource is not available, last borrowing date: " + resource.getLastBorrowingDate(),
                        HttpStatus.FORBIDDEN
                );
            }
            return new ResponseEntity<>("El resource is available", HttpStatus.OK);
        }  catch (RuntimeException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/borrow/{id}")
    public ResponseEntity<String> borrowResource(@PathVariable String id) {
        try {
            ResourceDto resource = service.getById(id);

            if(Boolean.FALSE.equals(resource.getAvailable())) {
                return new ResponseEntity<>(
                        "Resource is not available, last borrowing date: " + resource.getLastBorrowingDate(),
                        HttpStatus.FORBIDDEN
                );
            }
            resource.setAvailable(false);
            resource.setLastBorrowingDate(LocalDate.now());

            service.update(resource);
            return new ResponseEntity<>("OK", HttpStatus.OK);
        }  catch (RuntimeException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/giveBack/{id}")
    public ResponseEntity<String> giveBackResource(@PathVariable String id) {
        try {
            ResourceDto resource = service.getById(id);

            if(Boolean.TRUE.equals(resource.getAvailable())) {
                return new ResponseEntity<>(
                        "Do not giveBack this recourse, because it is not borrowed", HttpStatus.FORBIDDEN);
            }
            resource.setAvailable(true);
            service.update(resource);
            return new ResponseEntity<>("OK", HttpStatus.OK);
        }  catch (RuntimeException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/recommendations")
    public ResponseEntity<List<ResourceDto>> getByQuery(
            @RequestParam(value = "type", defaultValue = "") String type,
            @RequestParam(value = "theme", defaultValue = "") String theme
    ) {
        try {
            return new ResponseEntity<>(service.getAllByQueries(type, theme),HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
