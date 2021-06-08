package xws.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xws.auth.domain.User;
import xws.auth.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/search/{query}")
    public ResponseEntity search(@PathVariable String query) {
        return ResponseEntity.ok(userService.search(query));
    }

    @GetMapping(value = "/profile-view/{username}")
    public ResponseEntity getProfileInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }
}
