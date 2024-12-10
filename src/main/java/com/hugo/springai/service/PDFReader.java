package com.hugo.springai.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PDFReader {


    public List<Document> read(Resource pdfResource) {
        log.info("Starting to read PDF resource: {}", pdfResource.getFilename());
        List<Document> documents = extractDocumentsFromPdf(pdfResource);
        documents = cleanDocuments(documents);
        log.info("Finished reading PDF resource: {}", pdfResource.getFilename());

        return documents;
    }

    private List<Document> extractDocumentsFromPdf(Resource pdfResource) {
        PdfDocumentReaderConfig config = createPdfDocumentReaderConfig();
        PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(pdfResource, config);
        return pdfReader.get();
    }

    private PdfDocumentReaderConfig createPdfDocumentReaderConfig() {
        return PdfDocumentReaderConfig
            .builder()
            .withPageExtractedTextFormatter(new ExtractedTextFormatter.Builder()
                .withNumberOfBottomTextLinesToDelete(0)
                .withNumberOfTopPagesToSkipBeforeDelete(0)
                .build())
            .withPagesPerDocument(1)
            .build();
    }

    private List<Document> cleanDocuments(List<Document> documents) {
        return documents
            .stream()
            .map(doc -> Document
                .builder()
                .withId(doc.getId())
                .withContent(cleanText(doc.getContent()))
                .withMetadata(doc.getMetadata())
                .build())
            .collect(Collectors.toList());
    }

    private String cleanText(String text) {
        if (text == null) {
            return "";
        }
        text = text.replace("\0", "");
        text = text.replaceAll("[^\\x20-\\x7E\\x0A\\x0D\\p{IsHangul}]", "");
        return text
            .replaceAll("\\s+", " ")
            .trim();
    }
}
