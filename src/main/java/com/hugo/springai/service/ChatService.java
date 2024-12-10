package com.hugo.springai.service;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;

import com.hugo.springai.dto.request.ChatRequest;
import com.hugo.springai.dto.response.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatClient chatClient;

    public ChatResponse question(ChatRequest request, String sessionId) {
        return new ChatResponse(
            chatClient
                .prompt()
                .user(request.question())
                .advisors(
                    advisorSpec -> advisorSpec.param(
                        CHAT_MEMORY_CONVERSATION_ID_KEY, sessionId))
                .call()
                .content(),
            sessionId
        );
    }
}
