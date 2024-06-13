package com.example.repositories;

import com.example.models.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User> {
    Optional<User> findByLogin(String login);

    void updateBalance(User user);
}
