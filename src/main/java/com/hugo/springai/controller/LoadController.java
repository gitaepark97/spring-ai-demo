package com.hugo.springai.controller;

import com.hugo.springai.service.ResourceLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/load")
@RequiredArgsConstructor
public class LoadController {

    private final ResourceLoader resourceLoader;

    @PostMapping
    public void load() {
        resourceLoader.load();
    }
}
