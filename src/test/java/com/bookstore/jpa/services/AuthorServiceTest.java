package com.bookstore.jpa.services;

import com.bookstore.jpa.dtos.AuthorRecordDto;
import com.bookstore.jpa.models.AuthorModel;
import com.bookstore.jpa.repositories.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    AuthorRepository authorRepository;

    @InjectMocks
    AuthorService authorService;

    @Test
    void getAuthorById() {
        UUID id = UUID.randomUUID();
        AuthorModel author = new AuthorModel();
        author.setId(id);
        when(authorRepository.findById(id)).thenReturn(Optional.of(author));

        Optional<AuthorModel> result = authorService.getAuthorById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        verify(authorRepository).findById(id);
    }

    @Test
    void getAllAuthors() {
        List<AuthorModel> authors = List.of(new AuthorModel());
        when(authorRepository.findAll()).thenReturn(authors);

        List<AuthorModel> result = authorService.getAllAuthors();

        assertEquals(1, result.size());
        verify(authorRepository).findAll();
    }

    @Test
    void saveAuthor() {
        AuthorRecordDto dto = new AuthorRecordDto("Test Author");
        AuthorModel saved = new AuthorModel();
        saved.setName("Test Author");
        when(authorRepository.save(any(AuthorModel.class))).thenReturn(saved);

        AuthorModel result = authorService.saveAuthor(dto);

        assertEquals("Test Author", result.getName());
        verify(authorRepository).save(any(AuthorModel.class));
    }

    @Test
    void updateAuthor() {
        UUID id = UUID.randomUUID();
        AuthorRecordDto dto = new AuthorRecordDto("Updated Name");
        AuthorModel existing = new AuthorModel();
        existing.setId(id);
        when(authorRepository.findById(id)).thenReturn(Optional.of(existing));
        when(authorRepository.save(any(AuthorModel.class))).thenReturn(existing);

        Optional<AuthorModel> result = authorService.updateAuthor(id, dto);

        assertTrue(result.isPresent());
        assertEquals("Updated Name", result.get().getName());
        verify(authorRepository).findById(id);
        verify(authorRepository).save(existing);
    }

    @Test
    void deleteAuthor() {
        UUID id = UUID.randomUUID();

        authorService.deleteAuthor(id);

        verify(authorRepository).deleteById(id);
    }
}