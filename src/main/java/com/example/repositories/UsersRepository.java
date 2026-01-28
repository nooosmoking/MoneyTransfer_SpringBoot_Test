package com.example.repositories;

import com.example.models.User;
import java.util.List;
import java.util.Optional;

public interface UsersRepository extends CrudRepository<User> {
    Optional<User> findByLogin(String login);


    void addPhone(Long userId, String phone);
    void deletePhone(Long userId, String phone);
    boolean hasPhone(Long userId, String phone);

    void addEmail(Long userId, String email);
    void deleteEmail(Long userId, String email);
    boolean hasEmail(Long userId, String email);

    boolean isPhoneExists(String phone, Long excludeUserId);
    boolean isEmailExists(String email, Long excludeUserId);
}