package com.noobdevs.day8_live.service.impl;

import com.noobdevs.day8_live.dto.ChannelRequestDto;
import com.noobdevs.day8_live.dto.ChannelResponseDto;
import com.noobdevs.day8_live.dto.ChannelSettingResponseDto;
import com.noobdevs.day8_live.mapper.ChannelMapper;
import com.noobdevs.day8_live.mapper.ChannelSettingMapper;
import com.noobdevs.day8_live.model.Channel;
import com.noobdevs.day8_live.model.ChannelSetting;
import com.noobdevs.day8_live.repository.ChannelRepository;
import com.noobdevs.day8_live.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl  implements ChannelService {

    private final ChannelRepository channelRepository;
    private final ChannelMapper channelMapper;
    private final ChannelSettingMapper channelSettingMapper;

    @Override
    public ChannelResponseDto createChannel(ChannelRequestDto dto) {
        return channelMapper.toResponse(channelRepository.save(channelMapper.toEntity(dto)));
    }

    @Override
    public ChannelResponseDto getChannelById(Long id) {
        return channelMapper.toResponse(channelRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("channel is  not found")));
    }

    @Override
    public List<ChannelResponseDto> getAllChannels() {
        return channelRepository.findAll().stream()
                .map(channelMapper::toResponse)
                .toList();
    }

    @Override
    public ChannelSettingResponseDto getChannelSettings(Long id) {
        Channel channel = channelRepository.findById(id).orElseThrow(()-> new RuntimeException("channel is  not found"));
        return channelSettingMapper.toResponse(channel.getChannelSetting());
    }

//    @Override
//    public ChannelResponseDto getChannelById(Long id) {
//        return null;
//    }
//
//    @Override
//    public List<ChannelResponseDto> getAllChannels() {
//        return null;
//    }
//
//    @Override
//    public ChannelResponseDto updateChannel(Long id, ChannelRequestDto dto) {
//        return null;
//    }
//
//    @Override
//    public void deleteChannel() {

//    }
}
