package com.hugo.springai.controller;

import com.hugo.springai.dto.request.ChatRequest;
import com.hugo.springai.dto.response.ChatResponse;
import com.hugo.springai.service.ChatService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ChatResponse chat(
        @RequestBody ChatRequest request,
        @RequestParam(required = false) String sessionId
    ) {
        if (sessionId == null) {
            sessionId = UUID
                .randomUUID()
                .toString();
        }

        return chatService.question(request, sessionId);
    }
}
