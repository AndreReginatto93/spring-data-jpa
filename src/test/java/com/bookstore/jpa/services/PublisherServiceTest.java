package com.bookstore.jpa.services;

import com.bookstore.jpa.dtos.PublisherRecordDto;
import com.bookstore.jpa.models.PublisherModel;
import com.bookstore.jpa.repositories.PublisherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PublisherServiceTest {

    @Mock
    PublisherRepository publisherRepository;

    @InjectMocks
    PublisherService publisherService;

    @Test
    void getPublisherById() {
        UUID id = UUID.randomUUID();
        PublisherModel publisher = new PublisherModel();
        publisher.setId(id);
        when(publisherRepository.findById(id)).thenReturn(Optional.of(publisher));

        Optional<PublisherModel> result = publisherService.getPublisherById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        verify(publisherRepository).findById(id);
    }

    @Test
    void getAllPublishers() {
        List<PublisherModel> publishers = List.of(new PublisherModel());
        when(publisherRepository.findAll()).thenReturn(publishers);

        List<PublisherModel> result = publisherService.getAllPublishers();

        assertEquals(1, result.size());
        verify(publisherRepository).findAll();
    }

    @Test
    void savePublisher() {
        PublisherRecordDto dto = new PublisherRecordDto("Test Publisher");
        PublisherModel saved = new PublisherModel();
        saved.setName("Test Publisher");
        when(publisherRepository.save(any(PublisherModel.class))).thenReturn(saved);

        PublisherModel result = publisherService.savePublisher(dto);

        assertEquals("Test Publisher", result.getName());
        verify(publisherRepository).save(any(PublisherModel.class));
    }

    @Test
    void updatePublisher() {
        UUID id = UUID.randomUUID();
        PublisherRecordDto dto = new PublisherRecordDto("Updated Name");
        PublisherModel existing = new PublisherModel();
        existing.setId(id);
        when(publisherRepository.findById(id)).thenReturn(Optional.of(existing));
        when(publisherRepository.save(any(PublisherModel.class))).thenReturn(existing);

        Optional<PublisherModel> result = publisherService.updatePublisher(id, dto);

        assertTrue(result.isPresent());
        assertEquals("Updated Name", result.get().getName());
        verify(publisherRepository).findById(id);
        verify(publisherRepository).save(existing);
    }

    @Test
    void deletePublisher() {
        UUID id = UUID.randomUUID();

        publisherService.deletePublisher(id);

        verify(publisherRepository).deleteById(id);
    }
}