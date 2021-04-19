package com.lab4.demo.book.controllers;

import com.lab4.demo.book.services.BookService;
import com.lab4.demo.book.model.dto.BookDTO;

import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.report.ReportType;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@RestController
@RequestMapping(EM_FRONT_OFFICE)
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final ReportServiceFactory reportServiceFactory;

    @GetMapping
    public List<BookDTO> allItems() {
        return bookService.findAll();
    }

    @PostMapping
    public BookDTO create(@RequestBody @NonNull BookDTO book) {

        return bookService.create(book);
    }

    @PatchMapping
    public BookDTO edit(@RequestBody @NonNull BookDTO book) {
        return bookService.edit(book);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable @NonNull Long id) {
        bookService.delete(id);
    }

    @GetMapping(EXPORT_REPORT)
    public String exportReport(@PathVariable @NonNull String type) {
        return reportServiceFactory.getReportService(ReportType.valueOf(type)).export(bookService.findBooksOutOfStock());
    }

    @GetMapping(SEARCH)
    public List<BookDTO> allBooksMatched(@PathVariable @NonNull String keyword) {
        List<BookDTO> booksByAuthorTitleGenre = bookService.findBooksByAuthorTitleGenre(keyword);
        return booksByAuthorTitleGenre;
    }
}
