package com.produo.urlcatter.link;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkRepository repository;
    private final CodeGenerator codeGenerator = new CodeGenerator();

    @GetMapping("/")
    public String index() {
        return "/{code}\n/add";
    }

    @GetMapping("/{code}")
    public Object getLink(@PathVariable("code") String code) {
        LinkEntity link = repository.findByCode(code);
        Map<String, Object> response = new HashMap<>();
        if (link != null) {
            int dayTime = 86400000;
            if ((int) (link.getLastUse().getTime() / dayTime) == (int) (new Date().getTime() / dayTime)) {
                link.setLastUse(new Date());
                repository.save(link);
            }
            return new RedirectView(link.getOriginal());
        }
        response.put("error", "Such a link do not exist.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addLink(@RequestBody() HashMap<String, String> body) {
        String link = body.get("link");
        Map<String, Object> response = new HashMap<>();
        if (link == null) {
            response.put("error", "No link value.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        LinkEntity entity = repository.findByOriginal(link);
        if (entity != null) {
            response.put("code", entity.getCode());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        try {
            new URL(link);
        } catch (MalformedURLException e) {
            response.put("error", "This is not a link.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        String code = codeGenerator.getCode();
        repository.save(new LinkEntity(link, code));
        response.put("code", code);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
