package com.noobdevs.day8_live.service.impl;

import com.noobdevs.day8_live.dto.PlaylistRequestDto;
import com.noobdevs.day8_live.dto.PlaylistResponseDto;
import com.noobdevs.day8_live.mapper.PlaylistMapper;
import com.noobdevs.day8_live.model.PlayList;
import com.noobdevs.day8_live.model.Video;
import com.noobdevs.day8_live.repository.PlayListRepository;
import com.noobdevs.day8_live.repository.VideoRepository;
import com.noobdevs.day8_live.service.PlayListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayListServiceImpl implements PlayListService {

    private final PlayListRepository playListRepository;
    private final VideoRepository videoRepository;
    private final PlaylistMapper playlistMapper;

    @Override
    public PlaylistResponseDto createPlaylist(PlaylistRequestDto dto) {
        List<Video> videos = dto.getVideIds() != null
                ? videoRepository.findAllById(dto.getVideIds())
                : List.of();
        return playlistMapper.toResponse(playListRepository.save(playlistMapper.toEntity(dto, videos)));
    }

    @Override
    public PlaylistResponseDto getPlaylistById(Long id) {
        return playlistMapper.toResponse(playListRepository.findById(id).orElseThrow(()-> new RuntimeException("playlist not  found ")));
    }

    @Override
    public List<PlaylistResponseDto> getAllPlaylists() {
        return playListRepository.findAll().stream().map(playlistMapper::toResponse).toList();
    }

    @Override
    public PlaylistResponseDto updatePlaylist(Long id, PlaylistRequestDto dto) {
        PlayList playList = playListRepository.findById(id).orElseThrow(()->new RuntimeException("channel is not found"));
        playList.setName(dto.getName());
        if(dto.getVideIds() != null){
            playList.setVideos(videoRepository.findAllById(dto.getVideIds()));
        }
        return playlistMapper.toResponse(playListRepository.save(playList));
    }

    @Override
    public void deletePlaylist(Long id) {
        playListRepository.deleteById(id);
    }
}
