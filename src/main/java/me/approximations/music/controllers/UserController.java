package me.approximations.music.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary="Return info of a user by authentication token.")
    @ApiResponses(value={
            @ApiResponse(responseCode="200", description="Valid user."),
            @ApiResponse(responseCode="403", description="Unauthorized."),
    })
    @GetMapping("/me")
    public ResponseEntity<User> me(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(userDetails.getUser());
    }

    @Operation(summary="Return all albums of a user.")
    @ApiResponses(value={
            @ApiResponse(responseCode="200", description="Found albums."),
            @ApiResponse(responseCode="404", description="User not found."),
    })
    @GetMapping("/{id}/albums")
    public ResponseEntity<Set<Album>> getAllByUser(@PathVariable Long id) {
        return ResponseEntity.ok(albumService.getAllByUser(id));
    }
}
