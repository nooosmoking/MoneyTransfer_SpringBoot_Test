package com.example.services;

import com.example.exceptions.NoSuchUserException;
import com.example.exceptions.UserAlreadyExistsException;
import com.example.logger.Logger;
import com.example.models.User;
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
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String signUp(User user) throws UserAlreadyExistsException {
        String login = user.getLogin();
        if (usersRepository.findByLogin(login).isPresent()) {
            throw new UserAlreadyExistsException("User with login \"" + login + "\" already exists.");
        }
        String token = jwtTokenProvider.createToken(login);
        usersRepository.save(new User(login, passwordEncoder.encode(user.getPassword()), 0, token));
        Logger.getInstance().logSignUp(login);
        return token;
    }

    @Override
    public String signIn(User user) throws NoSuchUserException, AuthenticationException {
        String login = user.getLogin();
        Optional<User> optionalUser = usersRepository.findByLogin(login);
        if (optionalUser.isEmpty()) {
            throw new NoSuchUserException("No such user with login \"" + login + "\".");
        }
//        User user = optionalUser.get();
        if (!passwordEncoder.matches(user.getPassword(), user.getPassword())) {
            throw new AuthenticationException("Wrong password");
        }
        String token = jwtTokenProvider.createToken(login);
        user.setJwtToken(token);
        usersRepository.update(user);
        Logger.getInstance().logSignIn(login);
        return token;
    }
}