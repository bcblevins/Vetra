package com.bcb.vetra.controllers;

import com.bcb.vetra.daos.UserDao;
import com.bcb.vetra.models.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Controller class for the person model, only meant to retrieve persons that !isDoctor.
 */
@RestController
@RequestMapping(path = "/users")
public class UserController {
    private UserDao userDao;
    public UserController(UserDao userDao) {this.userDao = userDao;}
    @GetMapping
    public List<User> getAll() {
        return userDao.getUsers();
    }
    @GetMapping(path = "/{username}")
    public User get(@PathVariable String username) {
        return userDao.getUserByUsername(username);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User create(@Valid @RequestBody User user) {
        return userDao.createPerson(user);
    }
    @PutMapping(path = "/{username}")
    public User update(@Valid @RequestBody User user, @PathVariable String username) {
        return userDao.updatePerson(user);
    }
    @PutMapping(path = "/{username}/password")
    public User updatePassword(@Valid @RequestBody User user, @PathVariable String username) {
        return userDao.updatePassword(user);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{username}")
    public void delete(@PathVariable String username) {
        userDao.deleteUser(username);
    }
}
