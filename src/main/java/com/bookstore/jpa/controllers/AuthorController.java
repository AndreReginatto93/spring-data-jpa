package com.bookstore.jpa.controllers;

import com.bookstore.jpa.dtos.AuthorRecordDto;
import com.bookstore.jpa.models.AuthorModel;
import com.bookstore.jpa.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/bookstore/author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<AuthorModel>> getAuthorById(@PathVariable UUID id){
        Optional<AuthorModel> authorModel = authorService.getAuthorById(id);
        if (authorModel.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(authorModel);
        }
        return ResponseEntity.status(HttpStatus.OK).body(authorModel);
    }

    @GetMapping
    public ResponseEntity<List<AuthorModel>> getAllAuthors(){
        return ResponseEntity.status(HttpStatus.OK).body(authorService.getAllAuthors());
    }

    @PostMapping
    public ResponseEntity<AuthorModel> saveAuthor(@RequestBody AuthorRecordDto authorRecordDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(authorService.saveAuthor(authorRecordDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorModel> updateAuthor(
            @PathVariable UUID id,
            @RequestBody AuthorRecordDto authorRecordDto) {
        return authorService.updateAuthor(id, authorRecordDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable UUID id){
        authorService.deleteAuthor(id);
        return ResponseEntity.status(HttpStatus.OK).body("Author deleted successfully");
    }
}
