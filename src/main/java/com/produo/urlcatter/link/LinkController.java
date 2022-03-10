package com.produo.urlcatter.link;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

@RestController
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkRepository repository;

    @GetMapping("/")
    public String index() {
        return "/{code}\n/add";
    }

    @GetMapping("/{code}")
    public ResponseEntity<String> getLink(@PathVariable("code") String code) {
        return ResponseEntity.status(HttpStatus.OK).body(code);
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody() HashMap<String, Object> body) {
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }
}
