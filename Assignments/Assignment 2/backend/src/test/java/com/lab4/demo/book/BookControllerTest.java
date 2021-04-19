package com.lab4.demo.book;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;

import com.lab4.demo.book.controllers.BookController;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.book.services.BookService;
import com.lab4.demo.report.CSVReportService;
import com.lab4.demo.report.PdfReportService;
import com.lab4.demo.report.ReportServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.List;

import static com.lab4.demo.TestCreationFactory.randomLong;
import static com.lab4.demo.TestCreationFactory.randomString;
import static com.lab4.demo.UrlMapping.EM_FRONT_OFFICE;
import static com.lab4.demo.UrlMapping.EXPORT_REPORT;

import static com.lab4.demo.report.ReportType.CSV;
import static com.lab4.demo.report.ReportType.PDF;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookControllerTest extends BaseControllerTest {

    @InjectMocks
    private BookController controller;

    @Mock
    private BookService bookService;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @Mock
    private CSVReportService csvReportService;

    @Mock
    private PdfReportService pdfReportService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new BookController(bookService, reportServiceFactory);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allItems() throws Exception {
        List<BookDTO> books = TestCreationFactory.listOf(Book.class);
        when(bookService.findAll()).thenReturn(books);

        ResultActions response = mockMvc.perform(get(EM_FRONT_OFFICE));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(books));

    }

    @Test
    void exportReport() throws Exception {
        List<BookDTO> books = TestCreationFactory.listOf(Book.class);
        when(reportServiceFactory.getReportService(PDF)).thenReturn(pdfReportService);
        when(reportServiceFactory.getReportService(CSV)).thenReturn(csvReportService);
        String pdfResponse = "PDF!";
        when(pdfReportService.export(books)).thenReturn(pdfResponse);
        String csvResponse = "CSV!";
        when(csvReportService.export(books)).thenReturn(csvResponse);

        ResultActions pdfExport = mockMvc.perform(get(EM_FRONT_OFFICE + EXPORT_REPORT, PDF.name()));
        ResultActions csvExport = mockMvc.perform(get(EM_FRONT_OFFICE + EXPORT_REPORT, CSV.name()));

        pdfExport.andExpect(status().isOk())
                .andExpect(content().string(pdfResponse));
        csvExport.andExpect(status().isOk())
                .andExpect(content().string(csvResponse));

    }

    @Test
    void create() throws Exception {
        BookDTO reqItem = BookDTO.builder()
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .price(randomLong())
                .quantity(randomLong())
                .build();

        when(bookService.create(reqItem)).thenReturn(reqItem);

        ResultActions result = performPostWithRequestBody(EM_FRONT_OFFICE, reqItem);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqItem));
    }

    @Test
    void edit() throws Exception {
        BookDTO reqItem = BookDTO.builder()
                .id(randomLong())
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .price(randomLong())
                .quantity(randomLong())
                .build();

        when(bookService.edit(reqItem)).thenReturn(reqItem);

        ResultActions result = performPutWithRequestBody(EM_FRONT_OFFICE, reqItem);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqItem));
    }


    @Test
    void delete() throws Exception{
        BookDTO book = BookDTO.builder()
                .id(randomLong())
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .price(randomLong())
                .quantity(randomLong())
                .build();
        ResultActions result = performDeleteWithPathVariable(EM_FRONT_OFFICE + "/{id}",String.valueOf(book.getId()));


        result.andExpect(status().isOk());
    }


}