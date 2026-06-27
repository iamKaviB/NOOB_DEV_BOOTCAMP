package com.noobdevs.day7_test2.dto;

import lombok.Data;

import java.util.List;

@Data
public class FetchDemoResponseDTO {
    private String fetchMode;
    private int queriesGenerated;
    private String explanation;
    private String watchConsole;
    private Long userId;
    private String username;
    private int orderCount;
    private List<String> orderTrackingNumbers;
}
