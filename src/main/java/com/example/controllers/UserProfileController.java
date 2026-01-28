package com.example.controllers;

import com.example.models.UserChangeInfoRequest;
import com.example.services.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {
    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PostMapping("/profile")
    public ResponseEntity<Void> addUserInfo(@RequestBody UserChangeInfoRequest request) {
        userProfileService.addUserInfo(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/profile")
    public ResponseEntity<Void> deleteUserInfo(@RequestBody UserChangeInfoRequest request) {
        userProfileService.deleteUserInfo(request);
        return ResponseEntity.ok().build();
    }
}