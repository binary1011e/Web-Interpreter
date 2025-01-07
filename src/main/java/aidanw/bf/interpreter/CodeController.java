package aidanw.bf.interpreter;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class CodeController {

    @Autowired
    BFService bfService;
    private Map<String, Code> db = new HashMap<>() {{
        put("1", new Code("1", "<", "", "Lox"));
    }};


    @GetMapping("/code/")
    public Collection<Code> get() {
        return db.values();
    }

    @GetMapping("/code/{id}")
    public Code get(@PathVariable String id) {
        Code code = db.get(id);
        if (code == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return db.get(id);
    }

    @DeleteMapping("/code/{id}")
    public void delete(@PathVariable String id) {
        Code code = db.remove(id);
        if (code == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/code/")
    public Code create(@RequestBody @Valid Code code) {
        code.setId(UUID.randomUUID().toString());
        BFService.interpretCode(code);
        System.out.println(code.getInputCode());
        db.put(code.getId(), code);
        return code;
    }
}
