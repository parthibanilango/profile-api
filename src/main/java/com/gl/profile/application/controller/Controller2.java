package com.gl.profile.application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller2 {

    @GetMapping("/hello")
    public ResponseEntity<String> getSomeHello(){
        return ResponseEntity.ok("Success");
    }
}
