package com.noobdevs.day8_live.controller;


import com.noobdevs.day8_live.dto.VideoRequestDto;
import com.noobdevs.day8_live.dto.VideoResponseDto;
import com.noobdevs.day8_live.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @PostMapping
    public ResponseEntity<VideoResponseDto> createVideo(@RequestBody VideoRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(videoService.createVideo(dto));
    }

    @GetMapping
    public ResponseEntity<List<VideoResponseDto>> getAllVideos() {
        return ResponseEntity.ok(videoService.getAllVideos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoResponseDto> getVideoById(@PathVariable Long id) {
        return ResponseEntity.ok(videoService.getVideoById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideoResponseDto> updateVideo(@PathVariable Long id,
                                                         @RequestBody VideoRequestDto dto) {
        return ResponseEntity.ok(videoService.updateVideo(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id) {
        videoService.deleteVideo(id);
        return ResponseEntity.noContent().build();
    }
}
