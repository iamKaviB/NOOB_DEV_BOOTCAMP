package com.noobdevs.day8_live.service;

import com.noobdevs.day8_live.dto.PlaylistRequestDto;
import com.noobdevs.day8_live.dto.PlaylistResponseDto;

import java.util.List;

public interface PlayListService {
    PlaylistResponseDto createPlaylist(PlaylistRequestDto dto);
    PlaylistResponseDto getPlaylistById(Long id);
    List<PlaylistResponseDto> getAllPlaylists();
    PlaylistResponseDto updatePlaylist(Long id, PlaylistRequestDto dto);
    void deletePlaylist(Long id);
}
