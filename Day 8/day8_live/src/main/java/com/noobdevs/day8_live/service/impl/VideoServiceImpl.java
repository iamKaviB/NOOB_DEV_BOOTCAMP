package com.noobdevs.day8_live.service.impl;

import com.noobdevs.day8_live.dto.VideoRequestDto;
import com.noobdevs.day8_live.dto.VideoResponseDto;
import com.noobdevs.day8_live.mapper.ChannelMapper;
import com.noobdevs.day8_live.mapper.VideoMapper;
import com.noobdevs.day8_live.model.Channel;
import com.noobdevs.day8_live.model.Video;
import com.noobdevs.day8_live.repository.ChannelRepository;
import com.noobdevs.day8_live.repository.VideoRepository;
import com.noobdevs.day8_live.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private  final VideoMapper videoMapper;
    private final ChannelRepository channelRepository;

    @Override
    public VideoResponseDto createVideo(VideoRequestDto dto) {
        Channel chanel = channelRepository.findById(dto.getChannelId())
                .orElseThrow(()-> new RuntimeException("channel is not  found"));

        return videoMapper.toResponse(videoRepository.save(videoMapper.toEntity(dto,chanel)));
    }

    @Override
    public VideoResponseDto getVideoById(Long id) {
        return videoMapper.toResponse(videoRepository.findById(id).orElseThrow(()->new RuntimeException("video not found")));
    }

    @Override
    public List<VideoResponseDto> getAllVideos() {
        return videoRepository.findAll().stream().map(videoMapper::toResponse).toList();
    }

    @Override
    public VideoResponseDto updateVideo(Long id, VideoRequestDto dto) {
        Video video = videoRepository.findById(id).orElseThrow(()->new RuntimeException("video not found"));
        video.setTitle(dto.getTitle());
        video.setDurationInSeconds(dto.getDurationInSeconds());
        if(dto.getChannelId() != null && !dto.getChannelId().equals(video.getChannel().getId())){
            Channel channel = channelRepository.findById(dto.getChannelId()).orElseThrow(()->new RuntimeException("channel not found"));
            video.setChannel(channel);
        }
        return videoMapper.toResponse(videoRepository.save(video));
    }

    @Override
    public void deleteVideo(Long id) {
        videoRepository.deleteById(id);
    }
}
