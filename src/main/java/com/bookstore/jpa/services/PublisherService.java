package com.bookstore.jpa.services;


import com.bookstore.jpa.dtos.PublisherRecordDto;
import com.bookstore.jpa.models.PublisherModel;
import com.bookstore.jpa.repositories.PublisherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PublisherService {
    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public Optional<PublisherModel> getPublisherById(UUID id){
        return publisherRepository.findById(id);
    }

    public List<PublisherModel> getAllPublishers(){
        return publisherRepository.findAll();
    }

    @Transactional
    public PublisherModel savePublisher(PublisherRecordDto publisherRecordDto){
        PublisherModel publisherModel = new PublisherModel();
        publisherModel.setName(publisherRecordDto.name());

        return publisherRepository.save(publisherModel);
    }

    @Transactional
    public Optional<PublisherModel> updatePublisher(UUID id, PublisherRecordDto publisherRecordDto) {
        return publisherRepository.findById(id)
                .map(existingPublisher -> {
                    existingPublisher.setName(publisherRecordDto.name());
                    return publisherRepository.save(existingPublisher);
                });
    }

    @Transactional
    public void deletePublisher(UUID id){
        publisherRepository.deleteById(id);
    }
}
