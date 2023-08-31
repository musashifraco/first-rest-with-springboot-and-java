package com.example.demo.controllers;

import com.example.demo.data.vo.v1.BookVO;
import com.example.demo.data.vo.v1.PersonVO;
import com.example.demo.services.BookServices;
import com.example.demo.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/books/v1")
public class BookController {
    @Autowired
    private BookServices service;

    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_YML, MediaType.APPLICATION_XML})
    @Operation(summary = "Finds all books",
               description = "Finds all books",
               tags = {"Book"},
               responses = {@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
                            @ApiResponse(description = "Success",
                                         responseCode = "200",
                                         content = {@Content(mediaType = "application/json",
                                                             array = @ArraySchema(schema = @Schema(implementation = PersonVO.class)))})})
    public List<BookVO> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}",
                produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Finds a book",
               description = "Finds a book",
               tags = {"Book"},
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
    public BookVO findById(@PathVariable(value = "id") Long id) {
        return service.findById(id);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
                 produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Adds a book",
               description = "Adds a new book by passing in a JSON, XML or YML representation of the person!",
               tags = {"Book"},
               responses = {@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
                            @ApiResponse(description = "Success",
                                         responseCode = "200",
                                         content = @Content(schema = @Schema(implementation = PersonVO.class)))
               })
    public BookVO create(@RequestBody BookVO book) throws Exception {
        return service.create(book);
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
    public BookVO update(@RequestBody BookVO book) throws Exception {
        return service.update(book);
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
    }}
