package com.noobdevs.day8_live.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tbl_channel_setting")
public class ChannelSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isPrivate;
    private String regionRestrictions;

    @OneToOne(mappedBy = "channelSetting")
    private Channel channel;
}
