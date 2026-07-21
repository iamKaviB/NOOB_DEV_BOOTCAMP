package com.noobdevs.day8_live.dto;

import lombok.Data;

@Data
public class VideoRequestDto {
    private String title;
    private Integer durationInSeconds;
    private Long channelId;
}
