package aidanw.bf.interpreter;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
public class CodeController {

    @Autowired
    CodeRepo codeRepo;
    @Autowired
    UserRepo userRepo;
    @DeleteMapping("/code/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable int id) {
        codeRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/code/{id}")
    public ResponseEntity<Code> create(@RequestBody @Valid Code code, @PathVariable int id) {
        BFService.interpretCode(code);
        code.setUser(userRepo.getReferenceById(id));
        userRepo.getReferenceById(id).addCode(code);
        codeRepo.saveAndFlush(code);
        System.out.println(code.getInputCode());
        return new ResponseEntity<>(code, HttpStatus.OK);
    }
}
