package com.example.services;

import com.example.exceptions.UserNotFoundException;
import com.example.exceptions.ValidationException;
import com.example.models.User;
import com.example.models.UserChangeInfoRequest;
import com.example.repositories.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Transactional
public class UserProfileServiceImpl implements UserProfileService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileServiceImpl.class);

    private final UsersRepository usersRepository;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^\\+?[0-9]{10,15}$");

    public UserProfileServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return usersRepository.findByLogin(username)
                .orElseThrow(() -> new UserNotFoundException("Current user not found"));
    }

    @Override
    public void addUserInfo(UserChangeInfoRequest request) {
        validateAddRequest(request);
        User currentUser = getCurrentUser();

        if (request.getPhone() != null && !request.getPhone().trim().isEmpty()) {
            addPhone(currentUser, request.getPhone());
        } else if (request.getEmail() != null && !request.getEmail().trim().isEmpty()) {
            addEmail(currentUser, request.getEmail());
        }
    }

    @Override
    public void deleteUserInfo(UserChangeInfoRequest request) {
        validateDeleteRequest(request);
        User currentUser = getCurrentUser();

        if (request.getPhone() != null && !request.getPhone().trim().isEmpty()) {
            deletePhone(currentUser, request.getPhone());
        } else if (request.getEmail() != null && !request.getEmail().trim().isEmpty()) {
            deleteEmail(currentUser, request.getEmail());
        }
    }

    private void addPhone(User user, String phone) {
        validatePhone(phone);

        // Проверяем уникальность номера в системе
        if (usersRepository.isPhoneExists(phone, user.getId())) {
            throw new ValidationException("Phone number is already in use by another account");
        }

        // Проверяем, нет ли уже такого номера у пользователя
        if (usersRepository.hasPhone(user.getId(), phone)) {
            throw new ValidationException("Phone number already exists in your profile");
        }

        usersRepository.addPhone(user.getId(), phone);

        LOGGER.info("User {} added phone: {}", user.getId(), phone);
    }

    private void deletePhone(User user, String phone) {
        if (!usersRepository.hasPhone(user.getId(), phone)) {
            throw new ValidationException("Phone number not found in your profile");
        }

        usersRepository.deletePhone(user.getId(), phone);

        LOGGER.info("User {} deleted phone: {}", user.getId(), phone);
    }

    private void addEmail(User user, String email) {
        validateEmail(email);

        if (usersRepository.isEmailExists(email, user.getId())) {
            throw new ValidationException("Email is already in use by another account");
        }

        if (usersRepository.hasEmail(user.getId(), email)) {
            throw new ValidationException("Email already exists in your profile");
        }

        usersRepository.addEmail(user.getId(), email);

        LOGGER.info("User {} added email: {}", user.getId(), email);
    }

    private void deleteEmail(User user, String email) {
        if (!usersRepository.hasEmail(user.getId(), email)) {
            throw new ValidationException("Email not found in your profile");
        }

        usersRepository.deleteEmail(user.getId(), email);

        LOGGER.info("User {} deleted email: {}", user.getId(), email);
    }

    private void validateAddRequest(UserChangeInfoRequest request) {
        if (request == null) {
            throw new ValidationException("Request cannot be null");
        }

        boolean hasPhone = request.getPhone() != null && !request.getPhone().trim().isEmpty();
        boolean hasEmail = request.getEmail() != null && !request.getEmail().trim().isEmpty();

        if (!hasPhone && !hasEmail) {
            throw new ValidationException("Either phone or email must be provided");
        }

        if (hasPhone && hasEmail) {
            throw new ValidationException("Specify only one field (phone OR email) per request");
        }
    }

    private void validateDeleteRequest(UserChangeInfoRequest request) {
        if (request == null) {
            throw new ValidationException("Request cannot be null");
        }

        boolean hasPhone = request.getPhone() != null && !request.getPhone().trim().isEmpty();
        boolean hasEmail = request.getEmail() != null && !request.getEmail().trim().isEmpty();

        if (!hasPhone && !hasEmail) {
            throw new ValidationException("Either phone or email must be provided for deletion");
        }

        if (hasPhone && hasEmail) {
            throw new ValidationException("Specify only one field (phone OR email) to delete");
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("Email cannot be empty");
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new ValidationException("Invalid email format");
        }
    }

    private void validatePhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            throw new ValidationException("Phone cannot be empty");
        }

        String cleanPhone = phone.replaceAll("\\s+", "");
        if (!PHONE_PATTERN.matcher(cleanPhone).matches()) {
            throw new ValidationException(
                    "Invalid phone format. Use +1234567890 or 1234567890 (10-15 digits)"
            );
        }
    }
}