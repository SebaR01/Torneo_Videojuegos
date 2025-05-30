package com.torneo.api.controllers;

import com.torneo.api.dto.TestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @PostMapping("/test")
    public ResponseEntity<String> test(@RequestBody TestDto dto) {
        return ResponseEntity.ok("Hola " + dto.getNombre());
    }
}
