package com.lab4.demo.book.services;


import com.lab4.demo.book.mappers.BookMapper;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.book.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    private Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
    }

    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookDTO create(BookDTO book) {
        return bookMapper.toDto(bookRepository.save(
                bookMapper.fromDto(book)
        ));
    }

    public BookDTO edit(BookDTO book) {
        Book actBook = findById(book.getId());

        actBook.setAuthor(book.getAuthor());
        actBook.setTitle(book.getTitle());
        actBook.setGenre(book.getGenre());
        actBook.setQuantity(book.getQuantity());
        actBook.setPrice(book.getPrice());
        Book save = bookRepository.save(actBook);
        BookDTO bookDTO = bookMapper.toDto(save);

        return bookDTO;


    }

    public List<BookDTO> findBooksOutOfStock() {

        return bookRepository.findBooksByQuantity(0L).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        bookRepository.delete(book.get());
    }

    public List<BookDTO> findBooksByAuthorTitleGenre(String keyword) {
        return bookRepository.findBooksByGenreOrTitleOrAuthor(keyword, keyword, keyword).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());

    }

}
