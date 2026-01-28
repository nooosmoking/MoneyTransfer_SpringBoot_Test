package com.example.services;

import com.example.exceptions.NoSuchUserException;
import com.example.exceptions.UserAlreadyExistsException;
import com.example.models.User;
import com.example.repositories.UsersRepository;
import com.example.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void signUp(User user) throws UserAlreadyExistsException {
        String login = user.getLogin();
        if (usersRepository.findByLogin(login).isPresent()) {
            throw new UserAlreadyExistsException("User with login \"" + login + "\" already exists.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    @Override
    public String signIn(User user) throws NoSuchUserException, BadCredentialsException {
        String login = user.getLogin();
        Optional<User> optionalUser = usersRepository.findByLogin(login);
        if (optionalUser.isEmpty()) {
            throw new NoSuchUserException("No such user with login \"" + login + "\".");
        }

        User dbUser = optionalUser.get();
        if (!passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return jwtTokenProvider.createToken(login);
    }
}
