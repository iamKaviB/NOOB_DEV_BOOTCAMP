package com.noobdevs.day8_live.mapper;

import com.noobdevs.day8_live.dto.ChannelSettingRequestDto;
import com.noobdevs.day8_live.dto.ChannelSettingResponseDto;
import com.noobdevs.day8_live.model.ChannelSetting;
import org.springframework.stereotype.Component;

@Component
public class ChannelSettingMapper {

    public ChannelSetting toEntity(ChannelSettingRequestDto dto) {
        ChannelSetting setting = new ChannelSetting();
        setting.setIsPrivate(dto.getIsPrivate());
        setting.setRegionRestrictions(dto.getRegionRestrictions());
        return setting;
    }

    public ChannelSettingResponseDto toResponse(ChannelSetting setting) {
        ChannelSettingResponseDto dto = new ChannelSettingResponseDto();
        dto.setId(setting.getId());
        dto.setIsPrivate(setting.getIsPrivate());
        dto.setRegionRestriction(setting.getRegionRestrictions());
        return dto;
    }
}
