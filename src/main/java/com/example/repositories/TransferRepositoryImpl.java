package com.example.repositories;

import com.example.models.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository("transferRepository")
public class TransferRepositoryImpl implements TransferRepository {
    private final DataSource dataSource;

    @Autowired
    public TransferRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Payment> findAll() {
        return null;
    }

    @Override
    public Optional<Payment> findById(long id) {
        return Optional.empty();
    }

    @Override
    public void save(Payment entity) {
//        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
//        String query = "INSERT INTO transfers (amount, sender_id, receiver_id) VALUES (:amount, :sender_id, :receiver_id);";
//        jdbcTemplate.update(query, new MapSqlParameterSource().addValue("amount", entity.getAmount()).addValue("sender_id", entity.getSender().getId()).addValue("receiver_id", entity.getReceiver().getId()));
    }

    @Override
    public void update(Payment entity) {
    }

    @Override
    public void delete(Payment entity) {
    }
}
