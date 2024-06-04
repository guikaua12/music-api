package me.approximations.music.controllers;

import lombok.RequiredArgsConstructor;
import me.approximations.music.entities.User;
import me.approximations.music.security.entities.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(value="/user")
@RestController
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<User> me(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(userDetails.getUser());
    }

}
