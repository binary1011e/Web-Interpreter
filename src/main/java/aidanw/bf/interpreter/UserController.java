package aidanw.bf.interpreter;

import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
public class UserController {

    @Autowired
    private UserRepo userRepo;


    @GetMapping("/User/")
    public ResponseEntity<List<User>> get() {
        return new ResponseEntity<>(new ArrayList<>(userRepo.findAll()), HttpStatus.OK);
    }

    @PostMapping("/Username/")
    public ResponseEntity<User> get(@RequestBody String name) {
        Optional<User> userData = userRepo.findByName(name);
        return userData.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(()
                -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/User/{id}")
    public ResponseEntity<User> get(@PathVariable int id) {
        Optional<User> userData = userRepo.findById(id);
        return userData.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(()
                -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/User/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable int id) {
        userRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/User/")
    public ResponseEntity<User> create(@RequestBody @Valid User user) {
        User obj = null;
        if (userRepo.findByName(user.getName()).isPresent()) {
            obj = userRepo.findByName(user.getName()).get();
        }
        else {
            obj = userRepo.saveAndFlush(user);
        }
        System.out.println(obj.getId());
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }
}
