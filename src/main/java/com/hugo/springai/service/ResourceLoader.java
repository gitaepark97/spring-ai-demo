package com.hugo.springai.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ResourceLoader {

    private final VectorStore vectorStore;
    private final PDFReader pdfReader;

    @Value("classpath:/docs/reference.pdf")
    private Resource pdfResource;

    public void load() {
        log.info("Loading resource into vector store");
        List<Document> documents = pdfReader.read(pdfResource);
        documents = new TokenTextSplitter().apply(documents);
        vectorStore.accept(documents);
        log.info("Loaded resource into vector store");
    }
}
