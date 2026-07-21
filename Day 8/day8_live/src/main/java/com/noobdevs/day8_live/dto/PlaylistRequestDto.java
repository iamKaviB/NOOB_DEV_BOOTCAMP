package com.noobdevs.day8_live.dto;

import lombok.Data;

import java.util.List;

@Data
public class PlaylistRequestDto {
    private String name;
    private List<Long> videIds;
}
