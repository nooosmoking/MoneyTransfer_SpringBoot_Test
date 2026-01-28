package com.example.repositories;

import com.example.exceptions.DatabaseException;
import com.example.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class UsersRepositoryImpl implements UsersRepository {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    private static final RowMapper<User> USER_ROW_MAPPER = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("password"));
        user.setBalance(rs.getBigDecimal("balance"));
        return user;
    };

    public UsersRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT id, login, password, balance FROM users";
        return jdbcTemplate.query(sql, USER_ROW_MAPPER);
    }

    @Override
    public Optional<User> findById(long id) {
        String sql = "SELECT id, login, password, balance FROM users WHERE id = ?";

        try {
            User user = jdbcTemplate.queryForObject(sql, USER_ROW_MAPPER, id);
            return Optional.ofNullable(user);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> save(User entity) {
        if (entity.getId() == null) {
            // ВАЖНО: Указываем конкретные столбцы для возврата
            String sql = "INSERT INTO users (login, password, balance) VALUES (?, ?, ?)";

            KeyHolder keyHolder = new GeneratedKeyHolder();

            int rows = jdbcTemplate.update(connection -> {
                // Указываем имя столбца для возврата
                PreparedStatement ps = connection.prepareStatement(
                        sql,
                        new String[]{"id"}  // ← Указываем только id
                );
                ps.setString(1, entity.getLogin());
                ps.setString(2, entity.getPassword());
                ps.setBigDecimal(3, entity.getBalance() != null ? entity.getBalance() : BigDecimal.valueOf(0.0));
                return ps;
            }, keyHolder);

            if (rows == 1 && keyHolder.getKey() != null) {
                entity.setId(keyHolder.getKey().longValue());
                return Optional.of(entity);
            }
        } else {
            update(entity);
            return Optional.of(entity);
        }

        return Optional.empty();
    }
    @Override
    public void update(User entity) {
        String sql = "UPDATE users SET login = :login, password = :password, " +
                "balance = :balance WHERE id = :id";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("login", entity.getLogin())
                .addValue("password", entity.getPassword())
                .addValue("balance", entity.getBalance())
                .addValue("id", entity.getId());

        int rowsAffected = namedJdbcTemplate.update(sql, params);

        if (rowsAffected == 0) {
            throw new DatabaseException("User not found with id: " + entity.getId());
        }
    }

    @Override
    public void delete(User entity) {
        String sql = "DELETE FROM users WHERE id = ?";
        int rows = jdbcTemplate.update(sql, entity.getId());

        if (rows == 0) {
            throw new DatabaseException("User not found with id: " + entity.getId());
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        String sql = "SELECT id, login, password, balance FROM users WHERE login = ?";

        try {
            User user = jdbcTemplate.queryForObject(sql, USER_ROW_MAPPER, login);
            return Optional.ofNullable(user);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void addPhone(Long userId, String phone) {
        String sql = "INSERT INTO user_phones (user_id, phone_number) VALUES (?, ?)";

        try {
            int rows = jdbcTemplate.update(sql, userId, phone);
            if (rows != 1) {
                throw new DatabaseException("Failed to add phone for user: " + userId);
            }
        } catch (org.springframework.dao.DuplicateKeyException e) {
            throw new DatabaseException("Phone already exists for user: " + userId);
        }
    }

    @Override
    public void deletePhone(Long userId, String phone) {
        String sql = "DELETE FROM user_phones WHERE user_id = ? AND phone_number = ?";
        int rows = jdbcTemplate.update(sql, userId, phone);

        if (rows == 0) {
            throw new DatabaseException("Phone not found for user: " + userId);
        }
    }

    @Override
    public boolean hasPhone(Long userId, String phone) {
        String sql = "SELECT COUNT(*) FROM user_phones WHERE user_id = ? AND phone_number = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId, phone);
        return count > 0;
    }

    @Override
    public void addEmail(Long userId, String email) {
        String sql = "INSERT INTO user_emails (user_id, email) VALUES (?, ?)";

        try {
            int rows = jdbcTemplate.update(sql, userId, email);
            if (rows != 1) {
                throw new DatabaseException("Failed to add email for user: " + userId);
            }
        } catch (org.springframework.dao.DuplicateKeyException e) {
            throw new DatabaseException("Email already exists for user: " + userId);
        }
    }

    @Override
    public void deleteEmail(Long userId, String email) {
        String sql = "DELETE FROM user_emails WHERE user_id = ? AND email = ?";
        int rows = jdbcTemplate.update(sql, userId, email);

        if (rows == 0) {
            throw new DatabaseException("Email not found for user: " + userId);
        }
    }

    @Override
    public boolean hasEmail(Long userId, String email) {
        String sql = "SELECT COUNT(*) FROM user_emails WHERE user_id = ? AND email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId, email);
        return count > 0;
    }

    @Override
    public boolean isPhoneExists(String phone, Long excludeUserId) {
        String sql = "SELECT COUNT(*) FROM user_phones WHERE phone_number = ? AND user_id != ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, phone, excludeUserId);
        return count > 0;
    }

    @Override
    public boolean isEmailExists(String email, Long excludeUserId) {
        String sql = "SELECT COUNT(*) FROM user_emails WHERE email = ? AND user_id != ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email, excludeUserId);
        return count > 0;
    }
}