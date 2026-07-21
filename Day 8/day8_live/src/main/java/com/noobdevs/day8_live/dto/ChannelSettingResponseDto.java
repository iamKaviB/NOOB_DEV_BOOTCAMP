package com.noobdevs.day8_live.dto;

import lombok.Data;

@Data
public class ChannelSettingResponseDto {
    private Long id;
    private Boolean isPrivate;
    private String regionRestriction;
}
