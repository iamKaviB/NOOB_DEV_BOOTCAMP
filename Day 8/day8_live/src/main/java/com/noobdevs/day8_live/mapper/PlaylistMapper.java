package com.noobdevs.day8_live.mapper;

import com.noobdevs.day8_live.dto.PlaylistRequestDto;
import com.noobdevs.day8_live.dto.PlaylistResponseDto;
import com.noobdevs.day8_live.dto.VideoResponseDto;
import com.noobdevs.day8_live.model.PlayList;
import com.noobdevs.day8_live.model.Video;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PlaylistMapper {

    private final VideoMapper videoMapper;

    public PlayList toEntity(PlaylistRequestDto dto, List<Video> videos){
        PlayList playList = new PlayList();
        playList.setName(dto.getName());
        playList.setVideos(videos);
        return playList;
    }

    public PlaylistResponseDto toResponse(PlayList playList){
        PlaylistResponseDto dto = new PlaylistResponseDto();
        dto.setId(playList.getId());
        dto.setName(playList.getName());
        if(playList.getVideos() != null){
            dto.setVideos(playList.getVideos().stream().map(videoMapper::toResponse).toList());
        }
        return dto;
    }
}
