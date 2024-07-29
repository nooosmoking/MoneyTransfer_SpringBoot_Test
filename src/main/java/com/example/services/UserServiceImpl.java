package com.example.services;

import com.example.exceptions.NoSuchUserException;
import com.example.exceptions.UserAlreadyExistsException;
import com.example.logger.Logger;
import com.example.models.User;
import com.example.models.UserChangeInfoRequest;
import com.example.repositories.UsersRepository;
import com.example.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.sasl.AuthenticationException;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void signUp(User user) throws UserAlreadyExistsException {
        String login = user.getLogin();
        if (usersRepository.findByLogin(login).isPresent()) {
            throw new UserAlreadyExistsException("User with login \"" + login + "\" already exists.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
        Logger.getInstance().logSignUp(login);

    }

    @Override
    public void signIn(User user) throws NoSuchUserException {
        String login = user.getLogin();
        Optional<User> optionalUser = usersRepository.findByLogin(login);
        if (optionalUser.isEmpty()) {
            throw new NoSuchUserException("No such user with login \"" + login + "\".");
        }
        Logger.getInstance().logSignIn(login);
    }

    @Override
    public void changeInfo(UserChangeInfoRequest userChangeInfoRequest) {

    }

    @Override
    public void deleteInfo(UserChangeInfoRequest userChangeInfoRequest) {

    }

    @Override
    public void addInfo(UserChangeInfoRequest userChangeInfoRequest) {

    }
}
