package com.bookstore.jpa.controllers;

import com.bookstore.jpa.dtos.PublisherRecordDto;
import com.bookstore.jpa.models.PublisherModel;
import com.bookstore.jpa.services.PublisherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/bookstore/publisher")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<PublisherModel>> getPublisherById(@PathVariable UUID id){
        Optional<PublisherModel> publisherModel = publisherService.getPublisherById(id);
        if (publisherModel.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(publisherModel);
        }
        return ResponseEntity.status(HttpStatus.OK).body(publisherModel);
    }

    @GetMapping
    public ResponseEntity<List<PublisherModel>> getAllPublishers(){
        return ResponseEntity.status(HttpStatus.OK).body(publisherService.getAllPublishers());
    }

    @PostMapping
    public ResponseEntity<PublisherModel> savePublisher(@RequestBody PublisherRecordDto publisherRecordDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(publisherService.savePublisher(publisherRecordDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublisherModel> updatePublisher(
            @PathVariable UUID id,
            @RequestBody PublisherRecordDto publisherRecordDto) {
        return publisherService.updatePublisher(id, publisherRecordDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePublisher(@PathVariable UUID id){
        publisherService.deletePublisher(id);
        return ResponseEntity.status(HttpStatus.OK).body("Publisher deleted successfully");
    }
}
