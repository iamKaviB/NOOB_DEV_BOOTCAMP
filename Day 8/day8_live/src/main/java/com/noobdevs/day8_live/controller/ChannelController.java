package com.noobdevs.day8_live.controller;

import com.noobdevs.day8_live.dto.ChannelRequestDto;
import com.noobdevs.day8_live.dto.ChannelResponseDto;
import com.noobdevs.day8_live.dto.ChannelSettingResponseDto;
import com.noobdevs.day8_live.service.ChannelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/channels")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;

    @PostMapping
    public ResponseEntity<ChannelResponseDto> createChannel(@Valid @RequestBody ChannelRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(channelService.createChannel(dto));
    }

    @GetMapping
    public ResponseEntity<List<ChannelResponseDto>> getAllChannels() {
        return ResponseEntity.ok(channelService.getAllChannels());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChannelResponseDto> getChannelById(@PathVariable Long id) {
        return ResponseEntity.ok(channelService.getChannelById(id));
    }


    @GetMapping("/{id}/settings")
    public ResponseEntity<ChannelSettingResponseDto> getChannelSettings(@PathVariable Long id) {
        return ResponseEntity.ok(channelService.getChannelSettings(id));
    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ChannelResponseDto> updateChannel(@PathVariable Long id,
//                                                            @RequestBody ChannelRequestDto dto) {
//        return ResponseEntity.ok(channelService.updateChannel(id, dto));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteChannel(@PathVariable Long id) {
//        channelService.deleteChannel(id);
//        return ResponseEntity.noContent().build();
//    }

}
