package me.approximations.music.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.approximations.music.dtos.input.UploadSongDTO;
import me.approximations.music.entities.Song;
import me.approximations.music.services.song.SongService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin(origins="*")
@RequestMapping("/song")
@RestController
public class SongController {
    private final SongService songService;

    @Operation(summary="Upload a new song to a album.")
    @ApiResponses(value={
            @ApiResponse(responseCode="200", description="Uploaded song successfully."),
            @ApiResponse(responseCode="404", description="Album not found."),
            @ApiResponse(responseCode="415", description="Unsupported media type."),
    })
    @PutMapping("/upload")
    public ResponseEntity<Song> upload(@Valid UploadSongDTO dto) {
        return ResponseEntity.ok(songService.uploadSong(dto));
    }

    @Operation(summary="Search a song by name.")
    @ApiResponses(value={
            @ApiResponse(responseCode="200", description="Song found."),
    })
    @GetMapping("/search")
    public Page<Song> searchByName(@RequestParam("name") String name, Pageable pageable) {
        return songService.searchByName(name, pageable);
    }
}
