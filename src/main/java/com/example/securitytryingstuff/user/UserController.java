package com.example.securitytryingstuff.user;

import com.example.securitytryingstuff.user.model.User;
import com.example.securitytryingstuff.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.example.securitytryingstuff.UrlMapping.ENTITY;
import static com.example.securitytryingstuff.UrlMapping.USERS;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping(ENTITY)
    public User getUserAtId(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Couldn't find user at id: " + id));
    }

    @PostMapping
    public void createUser(@RequestBody User user) {
        System.out.println("Created user");
    }

    @PutMapping(ENTITY)
    public void editUser(@PathVariable Long id, @RequestBody User user) {
        System.out.println("Edited user");
    }

    @DeleteMapping(ENTITY)
    public void deleteUser(@PathVariable Long id) {
        System.out.println("Deleted user");
    }


}
