package xws.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.auth.domain.User;
import xws.auth.dto.UserDTO;
import xws.auth.exception.UsernameNotUniqueException;
import xws.auth.service.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/search/{query}")
    public ResponseEntity search(@PathVariable String query) {
        return ResponseEntity.ok(userService.search(query));
    }

    @PostMapping("/signup")
    public ResponseEntity<User> addUser(@RequestBody UserDTO userDTO) throws UsernameNotUniqueException {
        return new ResponseEntity(userService.create(userDTO), HttpStatus.CREATED);
    }
}
