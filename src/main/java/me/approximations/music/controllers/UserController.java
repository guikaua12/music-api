package me.approximations.music.controllers;

import lombok.RequiredArgsConstructor;
import me.approximations.music.entities.Album;
import me.approximations.music.entities.User;
import me.approximations.music.security.entities.CustomUserDetails;
import me.approximations.music.services.album.AlbumService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RequiredArgsConstructor
@RequestMapping(value="/user")
@RestController
public class UserController {
    private final AlbumService albumService;

    @GetMapping("/me")
    public ResponseEntity<User> me(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(userDetails.getUser());
    }

    @GetMapping("/{id}/album")
    public ResponseEntity<Set<Album>> getAllByUser(@PathVariable Long id) {
        return ResponseEntity.ok(albumService.getAllByUser(id));
    }

}
