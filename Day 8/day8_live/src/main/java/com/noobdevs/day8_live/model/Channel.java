package com.noobdevs.day8_live.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name="tbl_channel")
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "setting_id",referencedColumnName = "id")
    private ChannelSetting channelSetting;

    @OneToMany(mappedBy = "channel")
    private List<Video> videos;
}
