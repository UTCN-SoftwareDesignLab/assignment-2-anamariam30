package com.lab4.demo.report;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.lab4.demo.report.ReportType.CSV;
import static com.lab4.demo.report.ReportType.PDF;

@SpringBootTest
class ReportServiceFactoryTest {

    @Autowired
    private ReportServiceFactory reportServiceFactory;

    @Test
    void getReportService() {
        List<BookDTO> books = TestCreationFactory.listOf(BookDTO.class);
        ReportService csvReportService = reportServiceFactory.getReportService(CSV);
        Assertions.assertEquals("I am a CSV reporter.", csvReportService.export(books));

        ReportService pdfReportService = reportServiceFactory.getReportService(PDF);
        Assertions.assertEquals("I am a PDF reporter.", pdfReportService.export(books));
    }
}