package me.approximations.music.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.approximations.music.dtos.UploadSongDTO;
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

    @PutMapping("/upload")
    public ResponseEntity<Song> upload(@Valid UploadSongDTO dto) {
        return ResponseEntity.ok(songService.uploadSong(dto));
    }

    @GetMapping("/search")
    public Page<Song> searchByName(@RequestParam("name") String name, Pageable pageable) {
        return songService.searchByName(name, pageable);
    }
}
