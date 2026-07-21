package com.noobdevs.day8_live.dto;

import lombok.Data;

import java.util.List;

@Data
public class PlaylistResponseDto {

    private Long id;
    private String name;
    private List<VideoResponseDto> videos;

}
