package com.noobdevs.day8_live.dto;

import lombok.Data;

@Data
public class ChannelSettingRequestDto {

    private Boolean isPrivate;
    private String regionRestrictions;
}
