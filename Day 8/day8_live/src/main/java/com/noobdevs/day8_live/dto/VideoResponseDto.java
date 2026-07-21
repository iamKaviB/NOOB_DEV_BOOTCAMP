package com.noobdevs.day8_live.dto;

import lombok.Data;

@Data
public class VideoResponseDto {
    private Long id;
    private String title;
    private Integer durationInSeconds;
    private Long channelId;
}
