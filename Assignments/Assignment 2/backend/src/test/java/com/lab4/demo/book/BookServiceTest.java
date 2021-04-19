package com.lab4.demo.book;

import com.lab4.demo.TestCreationFactory;

import com.lab4.demo.book.mappers.BookMapper;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.book.repositories.BookRepository;
import com.lab4.demo.book.services.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.when;

class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(bookRepository, bookMapper);
    }

    @Test
    void findAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        when(bookRepository.findAll()).thenReturn(books);
        List<BookDTO> all = bookService.findAll();
        Assertions.assertEquals(books.size(), all.size());
    }

    @Test
    void create() {
        Long id = 1L;
        BookDTO bookDTO = BookDTO.builder()
                .id(id)
                .title("Title")
                .author("Author")
                .genre("Genre")
                .price(10L)
                .quantity(12L)
                .build();

        BookDTO methodCall = bookMapper.toDto(bookRepository.save(
                bookMapper.fromDto(bookDTO)));
        when(methodCall).thenReturn(bookDTO);
        BookDTO bookCreated = bookService.create(bookDTO);
        Assertions.assertEquals(bookCreated, bookDTO);

    }


    @Test
    void edit() {
        Long id = 1L;
        Book book = Book.builder()
                .id(id)
                .title("Title")
                .author("Author")
                .genre("Genre")
                .price(10L)
                .quantity(12L)
                .build();
        BookDTO bookDTO = BookDTO.builder()
                .id(id)
                .title("Title")
                .author("Author")
                .genre("Genre")
                .price(10L)
                .quantity(12L)
                .build();
        when(bookMapper.fromDto(bookDTO)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDTO);
        when(bookRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(book));
        when(bookRepository.save(book)).thenReturn(book);
        BookDTO editedBook = bookService.edit(bookDTO);
        Assertions.assertEquals(bookDTO, editedBook);


    }

    @Test
    void delete() {
        Long id = 1L;
        Book book = Book.builder()
                .id(id)
                .title("Title")
                .author("Author")
                .genre("Genre")
                .price(10L)
                .quantity(12L)
                .build();

        when(bookRepository.save(book)).thenReturn(book);
        when(bookRepository.findById(id)).thenReturn(java.util.Optional.of(book));

        bookService.delete(id);

    }

}