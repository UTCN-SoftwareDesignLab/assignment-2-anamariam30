package com.lab4.demo.report;


import com.lab4.demo.book.model.dto.BookDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.lab4.demo.report.ReportType.PDF;

@Service
public class PdfReportService implements ReportService {

    private static List<String> createPDFDataSimple(List<BookDTO> books) {
        List<String> list = new ArrayList<>();
        for (BookDTO book : books) {
            String record = book.getAuthor()+" -- "+book.getTitle()+ ";  ";
            list.add(record);
        }

        return list;
    }
    @Override
    public String export(List<BookDTO> books) {
        createPDF(createPDFDataSimple(books));


        return "I am a PDF reporter.";
    }

    public PDDocument createPDF(List<String> data)
    {
        PDDocument document=new PDDocument();
        PDPage page=new PDPage();
        document.addPage(page);
        try {
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            addDataToPage(contentStream,data);
            document.save("OutOfStock.pdf");
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    private void setConfiguration(PDPageContentStream contentStream ) throws IOException {
        contentStream.setLeading(16);
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.newLineAtOffset(50, 700);
        contentStream.showText("Books OUT of stock");
        contentStream.newLine();
        contentStream.newLine();
    }

    private void addDataToPage(PDPageContentStream contentStream,List<String> dataSimple) throws IOException {
        contentStream.beginText();
        setConfiguration(contentStream);
        for (String record : dataSimple)
        {
            contentStream.showText(record);
            contentStream.newLine();
        }
        contentStream.endText();
        contentStream.close();
    }



    @Override
    public ReportType getType() {
        return PDF;
    }
}
