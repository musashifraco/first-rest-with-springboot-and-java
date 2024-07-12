package com.example.demo.controllers;

import com.example.demo.data.vo.v1.PersonVO;
import com.example.demo.services.PersonServices;
import com.example.demo.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/person/v1")
@Tag(name = "People", description = "Endpoints for managing peoples")
public class PersonController {
    @Autowired
    private PersonServices service;

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping(value = "/{id}",
                produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Finds a people",
               description = "Finds a people",
               tags = {"People"},
               responses = {@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
                            @ApiResponse(description = "Success",
                                         responseCode = "200",
                                         content = @Content(schema = @Schema(implementation = PersonVO.class))
                            ),
                            @ApiResponse(description = "No Content",
                                         responseCode = "204",
                                         content = @Content)})
    public PersonVO findById(@PathVariable(value = "id") Long id) throws Exception {
        return service.findById(id);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Finds all people",
               description = "Finds all people",
               tags = {"People"},
               responses = {@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
                            @ApiResponse(description = "Success",
                                         responseCode = "200",
                                         content = {@Content(mediaType = "application/json",
                                                             array = @ArraySchema(schema = @Schema(implementation = PersonVO.class)))})})
    public ResponseEntity<Page<PersonVO>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                  @RequestParam(value = "limit", defaultValue = "12") Integer limit) throws Exception{
        Pageable pageable = PageRequest.of(page, limit);

        return ResponseEntity.ok(service.findAll(pageable));
    }

    @CrossOrigin(origins = {"http://localhost:8080", "https://demo.example.com"})
    @PostMapping(consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
                 produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Adds a people",
               description = "Adds a new Person by passing in a JSON, XML or YML representation of the person!",
               tags = {"People"},
               responses = {@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
                            @ApiResponse(description = "Success",
                                         responseCode = "200",
                                         content = @Content(schema = @Schema(implementation = PersonVO.class)))
               })
    public PersonVO create(@RequestBody PersonVO person) throws Exception {
        return service.create(person);
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
                produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Updates a people",
               description = "Updates a new Person by passing in a JSON, XML or YML representation of the person!",
               tags = {"People"},
               responses = {@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
                            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                            @ApiResponse(description = "Success",
                                         responseCode = "200",
                                         content = @Content(schema = @Schema(implementation = PersonVO.class)))
               })
    public PersonVO update(@RequestBody PersonVO person) throws Exception {
        return service.update(person);
    }

    @PatchMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Disable a specific person by your ID",
            description = "Disable a specific person by your ID",
            tags = {"People"},
            responses = {@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
                    @ApiResponse(description = "Success",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PersonVO.class))
                    ),
                    @ApiResponse(description = "No Content",
                            responseCode = "204",
                            content = @Content)})
    public PersonVO disablePerson(@PathVariable(value = "id") Long id) throws Exception {
        return service.disablePerson(id);
    }

    @DeleteMapping(value = "{id}")
    @Operation(summary = "Deletes a people",
               description = "Deletes a new Person by passing in a JSON, XML or YML representation of the person!",
               tags = {"People"},
               responses = {@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
                            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content)
               })
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

}
