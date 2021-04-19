package com.lab4.demo.report;

import com.lab4.demo.book.model.dto.BookDTO;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.lab4.demo.report.ReportType.CSV;

@Service
public class CSVReportService implements ReportService {

    private static List<String[]> createCsvDataSimple(List<BookDTO> books) {
        String[] header = {"Author", "Title", "Genre", "Price"};
        List<String[]> list = new ArrayList<>();
        list.add(header);
        for (BookDTO book : books) {
            String[] record = {book.getAuthor(), book.getTitle(), book.getGenre(), String.valueOf(book.getPrice())};
            list.add(record);
        }

        return list;
    }

    @Override
    public String export(List<BookDTO> books) {

        List<String[]> csvData = createCsvDataSimple(books);

        try (CSVWriter writer = new CSVWriter(new FileWriter("OutOfStock.csv"))) {
            writer.writeAll(csvData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "I am a CSV reporter.";
    }

    @Override
    public ReportType getType() {
        return CSV;
    }
}
