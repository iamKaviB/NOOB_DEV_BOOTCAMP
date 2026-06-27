package com.noobdevs.day7_test2.controller;

import com.noobdevs.day7_test2.dto.UserProfileRequestDTO;
import com.noobdevs.day7_test2.dto.UserProfileResponseDTO;
import com.noobdevs.day7_test2.service.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public ResponseEntity<List<UserProfileResponseDTO>> getAllProfiles() {
        return ResponseEntity.ok(userProfileService.getAllProfiles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponseDTO> getProfileById(@PathVariable Long id) {
        return ResponseEntity.ok(userProfileService.getProfileById(id));
    }

    @PostMapping
    public ResponseEntity<UserProfileResponseDTO> createProfile(@Valid @RequestBody UserProfileRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userProfileService.createProfile(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfileResponseDTO> updateProfile(@PathVariable Long id,
                                                                @Valid @RequestBody UserProfileRequestDTO dto) {
        return ResponseEntity.ok(userProfileService.updateProfile(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        userProfileService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }
}
