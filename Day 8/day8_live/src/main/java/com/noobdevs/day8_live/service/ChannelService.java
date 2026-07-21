package com.noobdevs.day8_live.service;

import com.noobdevs.day8_live.dto.ChannelRequestDto;
import com.noobdevs.day8_live.dto.ChannelResponseDto;
import com.noobdevs.day8_live.dto.ChannelSettingResponseDto;

import java.util.List;

public interface ChannelService {

    ChannelResponseDto createChannel(ChannelRequestDto dto);
    ChannelResponseDto getChannelById(Long id);
    List<ChannelResponseDto> getAllChannels();
//    ChannelResponseDto updateChannel(Long id, ChannelRequestDto dto);
//    void deleteChannel();

    ChannelSettingResponseDto getChannelSettings(Long id);

}
