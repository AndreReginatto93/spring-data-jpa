package com.bookstore.jpa.services;

import com.bookstore.jpa.dtos.BookRecordDto;
import com.bookstore.jpa.dtos.PublisherRecordDto;
import com.bookstore.jpa.models.AuthorModel;
import com.bookstore.jpa.models.BookModel;
import com.bookstore.jpa.models.PublisherModel;
import com.bookstore.jpa.models.ReviewModel;
import com.bookstore.jpa.repositories.AuthorRepository;
import com.bookstore.jpa.repositories.BookRepository;
import com.bookstore.jpa.repositories.PublisherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    BookRepository bookRepository;
    @Mock
    AuthorRepository AuthorRepository;
    @Mock
    PublisherRepository publisherRepository;

    @InjectMocks
    BookService bookService;

    @Test
    void getBookById() {
        UUID id = UUID.randomUUID();
        BookModel book = new BookModel();
        book.setId(id);
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        Optional<BookModel> result = bookService.getBookById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        verify(bookRepository).findById(id);
    }

    @Test
    void getAllBooks() {
        List<BookModel> books = List.of(new BookModel());
        when(bookRepository.findAll()).thenReturn(books);

        List<BookModel> result = bookService.getAllBooks();

        assertEquals(1, result.size());
        verify(bookRepository).findAll();
    }

    @Test
    void saveBook() {
        /*
        * Set<UUID> authors = new HashSet<>();
        authors.add(UUID.randomUUID());
        BookRecordDto dto = new BookRecordDto("Test Book", UUID.randomUUID(), authors, "Review");
        BookModel saved = new BookModel();
        saved.setTitle("Test Book");
        when(bookRepository.save(any(BookModel.class))).thenReturn(saved);

        BookModel result = bookService.saveBook(dto);

        assertEquals("Test Book", result.getTitle());
        verify(bookRepository).save(any(BookModel.class));
        *
        * */

        UUID publisherId = UUID.randomUUID();
        Set<UUID> authorIds = Set.of(UUID.randomUUID());
        String title = "Test Book";
        String reviewComment = "Great book!";

        BookRecordDto dto = new BookRecordDto(title, publisherId, authorIds, reviewComment);

        PublisherModel publisher = new PublisherModel();
        publisher.setId(publisherId);

        AuthorModel author = new AuthorModel();
        author.setId(authorIds.iterator().next());

        when(publisherRepository.findById(publisherId)).thenReturn(Optional.of(publisher));
        when(AuthorRepository.findAllById(authorIds)).thenReturn(List.of(author));
        when(bookRepository.save(any(BookModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BookModel result = bookService.saveBook(dto);

        assertEquals(title, result.getTitle());
        assertEquals(publisher, result.getPublisher());
        assertTrue(result.getAuthors().contains(author));
        assertNotNull(result.getReview());
        assertEquals(reviewComment, result.getReview().getComment());

        verify(publisherRepository).findById(publisherId);
        verify(AuthorRepository).findAllById(authorIds);
        verify(bookRepository).save(any(BookModel.class));
    }

    @Test
    void updateBook() {
        UUID id = UUID.randomUUID();
        Set<UUID> authors = new HashSet<>();
        authors.add(UUID.randomUUID());
        BookRecordDto dto = new BookRecordDto("Updated Name", UUID.randomUUID(), authors, "Review");
        BookModel existing = new BookModel();
        existing.setId(id);
        when(bookRepository.findById(id)).thenReturn(Optional.of(existing));
        when(bookRepository.save(any(BookModel.class))).thenReturn(existing);

        Optional<BookModel> result = bookService.updateBook(id, dto);

        assertTrue(result.isPresent());
        assertEquals("Updated Name", result.get().getTitle());
        verify(bookRepository).findById(id);
        verify(bookRepository).save(existing);
    }

    @Test
    void deleteBook() {

        UUID id = UUID.randomUUID();

        bookService.deleteBook(id);

        verify(bookRepository).deleteById(id);
    }
}