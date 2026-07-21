package com.noobdevs.day8_live.dto;

import lombok.Data;

@Data
public class ChannelResponseDto {

    private Long id;
    private String name;
    private ChannelSettingResponseDto settings;
}
