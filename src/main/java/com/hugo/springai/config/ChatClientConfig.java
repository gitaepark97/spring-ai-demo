package com.hugo.springai.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClient.Builder;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@RequiredArgsConstructor
public class ChatClientConfig {

    private final Builder builder;
    private final VectorStore vectorStore;

    @Value("classpath:/prompts/system-message.st")
    private Resource systemMessageTemplate;

    @Bean
    public ChatClient chatClient() throws IOException {
        return builder
            .defaultAdvisors(
                new MessageChatMemoryAdvisor(new InMemoryChatMemory()),
                QuestionAnswerAdvisor
                    .builder(vectorStore)
                    .withUserTextAdvise(
                        systemMessageTemplate.getContentAsString(StandardCharsets.UTF_8))
                    .withSearchRequest(SearchRequest
                        .defaults()
                        .withTopK(3))
                    .build()
            )
            .build();
    }
}