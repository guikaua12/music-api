package me.approximations.music.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary="Return a album by its id.")
    @ApiResponses(value={
            @ApiResponse(responseCode="200", description="Album found."),
            @ApiResponse(responseCode="404", description="Album not found."),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Album> findById(@PathVariable Long id) {
        return ResponseEntity.ok(albumService.findById(id));
    }

    @Operation(summary="Create a new album.")
    @ApiResponses(value={
            @ApiResponse(responseCode="200", description="Album created."),
            @ApiResponse(responseCode="404", description="User not found."),
    })
    @PostMapping("/create")
    public ResponseEntity<Album> create(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody @Valid CreateAlbumDTO dto) {
        return ResponseEntity.ok(albumService.create(dto, userDetails.getUser()));
    }
}

