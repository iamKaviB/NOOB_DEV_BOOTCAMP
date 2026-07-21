package com.noobdevs.day8_live.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChannelRequestDto {
    @NotBlank(message = "channel name is required")
    private String name;
    private ChannelSettingRequestDto settings;
}
