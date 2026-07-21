package com.noobdevs.day8_live.mapper;


import com.noobdevs.day8_live.dto.VideoRequestDto;
import com.noobdevs.day8_live.dto.VideoResponseDto;
import com.noobdevs.day8_live.model.Channel;
import com.noobdevs.day8_live.model.Video;
import org.springframework.stereotype.Component;

@Component
public class VideoMapper {

    public Video toEntity(VideoRequestDto dto, Channel channel) {
        Video video = new Video();
        video.setTitle(dto.getTitle());
        video.setDurationInSeconds(dto.getDurationInSeconds());
        video.setChannel(channel);
        return video;
    }

    public VideoResponseDto toResponse(Video video) {
        VideoResponseDto dto = new VideoResponseDto();
        dto.setId(video.getId());
        dto.setTitle(video.getTitle());
        dto.setDurationInSeconds(video.getDurationInSeconds());
        dto.setChannelId(video.getChannel().getId());
        return dto;
    }
}
