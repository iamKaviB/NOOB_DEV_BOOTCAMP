package com.noobdevs.day7_test2.controller;

import com.noobdevs.day7_test2.dto.FetchDemoResponseDTO;
import com.noobdevs.day7_test2.service.FetchDemoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo/fetch")
public class FetchDemoController {

    private final FetchDemoService fetchDemoService;

    public FetchDemoController(FetchDemoService fetchDemoService) {
        this.fetchDemoService = fetchDemoService;
    }

    // ── STEP 1 ──────────────────────────────────────────────────────────────
    // Hit this first. Watch the console — 2 SQL SELECT statements appear.
    // Then go to User.java, change FetchType.LAZY → EAGER, restart, and hit
    // this same endpoint again. The console now shows 1 SQL with a LEFT JOIN.
    // ────────────────────────────────────────────────────────────────────────
    @GetMapping("/{userId}/lazy")
    public ResponseEntity<FetchDemoResponseDTO> lazy(@PathVariable Long userId) {
        return ResponseEntity.ok(fetchDemoService.demonstrateLazy(userId));
    }

    // ── STEP 2 ──────────────────────────────────────────────────────────────
    // Hit this without changing the entity annotation.
    // Uses @Query JOIN FETCH — always 1 SQL regardless of FetchType on the entity.
    // ────────────────────────────────────────────────────────────────────────
    @GetMapping("/{userId}/eager")
    public ResponseEntity<FetchDemoResponseDTO> eager(@PathVariable Long userId) {
        return ResponseEntity.ok(fetchDemoService.demonstrateEager(userId));
    }

    // ── STEP 3 ──────────────────────────────────────────────────────────────
    // Intentionally causes LazyInitializationException.
    // Shows students what happens when @Transactional is missing and the
    // Hibernate session closes before the LAZY collection is accessed.
    // Response: HTTP 500 with error explanation and 3 ways to fix it.
    // ────────────────────────────────────────────────────────────────────────
    @GetMapping("/{userId}/lazy-fail")
    public ResponseEntity<FetchDemoResponseDTO> lazyFail(@PathVariable Long userId) {
        return ResponseEntity.ok(fetchDemoService.demonstrateLazyFail(userId));
    }
}
