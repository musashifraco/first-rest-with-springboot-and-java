package com.example.demo.controllers;

import com.example.demo.data.vo.v1.BookVO;
import com.example.demo.services.BookServices;
import com.example.demo.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                                                             array = @ArraySchema(schema = @Schema(implementation = BookVO.class)))})})
    public ResponseEntity<PagedModel<EntityModel<BookVO>>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                   @RequestParam(value = "size", defaultValue = "12") Integer size,
                                                                   @RequestParam(value = "direction", defaultValue = "asc") String direction) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "author"));

        return ResponseEntity.ok(service.findAll(pageable));
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
                                         content = @Content(schema = @Schema(implementation = BookVO.class))
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
               description = "Adds a new book by passing in a JSON, XML or YML representation of the book!",
               tags = {"Book"},
               responses = {@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
                            @ApiResponse(description = "Success",
                                         responseCode = "200",
                                         content = @Content(schema = @Schema(implementation = BookVO.class)))
               })
    public BookVO create(@RequestBody BookVO book) throws Exception {
        return service.create(book);
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
                produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Updates a people",
               description = "Updates a new Book by passing in a JSON, XML or YML representation of the book!",
               tags = {"People"},
               responses = {@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
                            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                            @ApiResponse(description = "Success",
                                         responseCode = "200",
                                         content = @Content(schema = @Schema(implementation = BookVO.class)))
               })
    public BookVO update(@RequestBody BookVO book) throws Exception {
        return service.update(book);
    }

    @DeleteMapping(value = "{id}")
    @Operation(summary = "Deletes a people",
               description = "Deletes a new Book by passing in a JSON, XML or YML representation of the book!",
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
