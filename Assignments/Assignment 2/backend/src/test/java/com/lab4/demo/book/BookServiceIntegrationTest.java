package com.lab4.demo.book;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.book.repositories.BookRepository;
import com.lab4.demo.book.services.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BookServiceIntegrationTest {


    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    void findAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        bookRepository.saveAll(books);
        List<BookDTO> all = bookService.findAll();
        Assertions.assertEquals(books.size(), all.size());
    }

    @Test
    void create() {
        BookDTO book = TestCreationFactory.newBookDTO();
        BookDTO createdBook = bookService.create(book);
        book.setId(createdBook.getId());
        Assertions.assertEquals(createdBook, book);
    }

    @Test
    void edit() {

        BookDTO newBook =TestCreationFactory.newBookDTO();
        BookDTO book = bookService.create(TestCreationFactory.newBookDTO());
        newBook.setId(book.getId());
        bookService.edit(newBook);
        List<BookDTO> books = bookService.findAll();
        Assertions.assertTrue(books.contains(newBook));
        Assertions.assertFalse(books.contains(book));
    }

    @Test
    void delete() {
        BookDTO book =bookService.create(TestCreationFactory.newBookDTO());

        bookService.delete(book.getId());

        Assertions.assertEquals(bookService.findAll().size(),0);
    }

    @Test
    void findBooksOutOfStock() {
        BookDTO book=TestCreationFactory.newBookDTO();
        book.setQuantity(0);
        BookDTO bookOutOfStock =bookService.create(book);
        BookDTO bookNotOutOfStock =bookService.create(TestCreationFactory.newBookDTO());

        List<BookDTO> booksOutOfStock=bookService.findBooksOutOfStock();

        Assertions.assertTrue(booksOutOfStock.contains(bookOutOfStock));
        Assertions.assertFalse(booksOutOfStock.contains(bookNotOutOfStock));
    }

    @Test
    void findBooksByAuthorTitleGenre() {

        BookDTO bookByAuthor =bookService.create(TestCreationFactory.newBookDTO());
        BookDTO bookByGenre =bookService.create(TestCreationFactory.newBookDTO());
        BookDTO bookByTitle =bookService.create(TestCreationFactory.newBookDTO());

        String author=bookByAuthor.getAuthor();
        String genre=bookByGenre.getGenre();
        String title=bookByTitle.getTitle();

        List<BookDTO> foundBookByAuthor =bookService.findBooksByAuthorTitleGenre(author);
        List<BookDTO> foundBookByGenre =bookService.findBooksByAuthorTitleGenre(genre);
        List<BookDTO> foundBookByTitle =bookService.findBooksByAuthorTitleGenre(title);

        Assertions.assertEquals(bookByAuthor,foundBookByAuthor.get(0));
        Assertions.assertEquals(bookByGenre,foundBookByGenre.get(0));
        Assertions.assertEquals(bookByTitle,foundBookByTitle.get(0));

    }


}