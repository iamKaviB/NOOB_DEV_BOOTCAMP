package com.noobdevs.day8_live.controller;

import com.noobdevs.day8_live.dto.PlaylistRequestDto;
import com.noobdevs.day8_live.dto.PlaylistResponseDto;
import com.noobdevs.day8_live.service.PlayListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
@RequiredArgsConstructor
public class PlayListController {

    private final PlayListService playListService;

    @PostMapping
    public ResponseEntity<PlaylistResponseDto> createPlaylist(@RequestBody PlaylistRequestDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(playListService.createPlaylist(dto));
    }

    @GetMapping
    public ResponseEntity<List<PlaylistResponseDto>> getAllPlaylists() {
        return ResponseEntity.ok(playListService.getAllPlaylists());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistResponseDto> getPlaylistById(@PathVariable Long id) {
        return ResponseEntity.ok(playListService.getPlaylistById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaylistResponseDto> updatePlaylist(@PathVariable Long id,
                                                              @RequestBody PlaylistRequestDto dto) {
        return ResponseEntity.ok(playListService.updatePlaylist(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long id) {
        playListService.deletePlaylist(id);
        return ResponseEntity.noContent().build();
    }
}
