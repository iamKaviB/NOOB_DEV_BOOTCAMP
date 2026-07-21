package com.noobdevs.day8_live.repository;

import com.noobdevs.day8_live.model.ChannelSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelSettingRepository extends JpaRepository<ChannelSetting,Long> {
}
