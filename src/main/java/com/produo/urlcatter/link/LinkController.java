package com.produo.urlcatter.link;

import com.produo.urlcatter.utils.CodeGenerator;
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
import java.util.regex.Pattern;

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
        Map<String, Object> response = new HashMap<>();

        Pattern pattern = Pattern.compile("[a-zA-Z0-9]{4,9}");
        if (!pattern.matcher(code).matches()) {
            response.put("error", "Invalid code");
            return failure(response);
        }

        LinkEntity link = repository.findByCode(code);
        if (link != null) {
            int dayTime = 86400000;
            if ((int) (link.getLastUse().getTime() / dayTime) == (int) (new Date().getTime() / dayTime)) {
                link.setLastUse(new Date());
                repository.save(link);
            }
            return new RedirectView(link.getOriginal());
        }
        
        response.put("error", "Such a link do not exist.");
        return failure(response);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addLink(@RequestBody() HashMap<String, String> body) {
        String link = body.get("link");
        Map<String, Object> response = new HashMap<>();
        if (link == null) {
            response.put("error", "No link value.");
            return failure(response);
        }

        LinkEntity entity = repository.findByOriginal(link);
        if (entity != null) {
            response.put("code", entity.getCode());
            return success(response);
        }
        try {
            if (link.length() > 512) {
                response.put("error", "Max link length exceeded");
                return failure(response);
            }
            new URL(link);
        } catch (MalformedURLException e) {
            response.put("error", "This is not a link.");
            return failure(response);
        }
        String code = codeGenerator.generate();
        repository.save(new LinkEntity(link, code));
        response.put("code", code);
        return success(response);
    }

    ResponseEntity<Object> success(Object obj) {
        return ResponseEntity.status(HttpStatus.CREATED).body(obj);
    }

    ResponseEntity<Object> failure(Object obj) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(obj);
    }
}
