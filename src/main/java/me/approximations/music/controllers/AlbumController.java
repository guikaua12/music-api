package me.approximations.music.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.approximations.music.dtos.CreateAlbumDTO;
import me.approximations.music.entities.Album;
import me.approximations.music.security.entities.CustomUserDetails;
import me.approximations.music.services.album.AlbumService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/album")
@RestController
@Validated
public class AlbumController {
    private final AlbumService albumService;

    @GetMapping("/{id}")
    public ResponseEntity<Album> findById(@PathVariable Long id) {
        return ResponseEntity.ok(albumService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Album> create(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody @Valid CreateAlbumDTO dto) {
        return ResponseEntity.ok(albumService.create(dto, userDetails.getUser()));
    }
}

