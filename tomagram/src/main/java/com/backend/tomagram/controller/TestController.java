package com.backend.tomagram.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test-controller")
public class TestController {
    @GetMapping
    public ResponseEntity<String> sayHi(){
        return ResponseEntity.ok("Teff 3la 3ammo y7abeebi");
    }
}
