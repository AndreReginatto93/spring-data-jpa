package com.bookstore.jpa.services;

import com.bookstore.jpa.dtos.AuthorRecordDto;
import com.bookstore.jpa.models.AuthorModel;
import com.bookstore.jpa.repositories.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Optional<AuthorModel> getAuthorById(UUID id){
        return authorRepository.findById(id);
    }

    public List<AuthorModel> getAllAuthors(){
        return authorRepository.findAll();
    }

    @Transactional
    public AuthorModel saveAuthor(AuthorRecordDto authorRecordDto){
        AuthorModel authorModel = new AuthorModel();
        authorModel.setName(authorRecordDto.name());

        return authorRepository.save(authorModel);
    }

    @Transactional
    public Optional<AuthorModel> updateAuthor(UUID id, AuthorRecordDto authorRecordDto) {
        return authorRepository.findById(id)
                .map(existingAuthor -> {
                    existingAuthor.setName(authorRecordDto.name());
                    return authorRepository.save(existingAuthor);
                });
    }

    @Transactional
    public void deleteAuthor(UUID id){
        authorRepository.deleteById(id);
    }
}
