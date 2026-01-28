package com.example.services;

import com.example.models.UserChangeInfoRequest;

public interface UserProfileService {
    void addUserInfo(UserChangeInfoRequest request);

    void deleteUserInfo(UserChangeInfoRequest request);
}
