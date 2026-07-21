package com.noobdevs.day8_live.mapper;

import com.noobdevs.day8_live.dto.ChannelRequestDto;
import com.noobdevs.day8_live.dto.ChannelResponseDto;
import com.noobdevs.day8_live.model.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChannelMapper {

    private final ChannelSettingMapper channelSettingMapper;

    public Channel toEntity(ChannelRequestDto dto) {
        Channel channel = new Channel();
        channel.setName(dto.getName());
        if (dto.getSettings() != null) {
            channel.setChannelSetting(channelSettingMapper.toEntity(dto.getSettings()));
        }
        return channel;
    }

    public ChannelResponseDto toResponse(Channel channel) {
        ChannelResponseDto dto = new ChannelResponseDto();
        dto.setId(channel.getId());
        dto.setName(channel.getName());
        if (channel.getChannelSetting() != null) {
            dto.setSettings(channelSettingMapper.toResponse(channel.getChannelSetting()));
        }
        return dto;
    }
}
