package com.noobdevs.day8_live.service;


import com.noobdevs.day8_live.dto.VideoRequestDto;
import com.noobdevs.day8_live.dto.VideoResponseDto;

import java.util.List;

public interface VideoService {
    VideoResponseDto createVideo(VideoRequestDto dto);
    VideoResponseDto getVideoById(Long id);
    List<VideoResponseDto> getAllVideos();
    VideoResponseDto updateVideo(Long id, VideoRequestDto dto);
    void deleteVideo(Long id);
}
