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
        LinkEntity link = repository.findByCode(code);
        if (link != null)
            return ResponseEntity.status(HttpStatus.OK).body(link.getOriginal());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no link");
    }

    @PostMapping("/add")
    public ResponseEntity<String> addLink(@RequestBody() HashMap<String, String> body) {
        String link = body.get("link");
        if (link == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no link value");
        String code = "todo";
        repository.save(new LinkEntity(link, code));
        return ResponseEntity.status(HttpStatus.CREATED).body(code);
    }
}
