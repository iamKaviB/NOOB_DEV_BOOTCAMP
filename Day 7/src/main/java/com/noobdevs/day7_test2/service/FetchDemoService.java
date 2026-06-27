package com.noobdevs.day7_test2.service;

import com.noobdevs.day7_test2.dto.FetchDemoResponseDTO;

public interface FetchDemoService {
    FetchDemoResponseDTO demonstrateLazy(Long userId);
    FetchDemoResponseDTO demonstrateEager(Long userId);
    FetchDemoResponseDTO demonstrateLazyFail(Long userId);
}
