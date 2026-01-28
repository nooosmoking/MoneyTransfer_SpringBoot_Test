package com.example.repositories;

import com.example.models.Transfer;
import com.example.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("transferRepository")
public class TransferRepositoryImpl implements TransferRepository {
    private final DataSource dataSource;

    @Autowired
    public TransferRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Transfer mapRowToTransfer(ResultSet rs) throws SQLException {
        Transfer transfer = new Transfer();
        transfer.setId(rs.getLong("id"));
        transfer.setAmount(rs.getBigDecimal("amount"));
        transfer.setTimestamp(rs.getTimestamp("date_time").toLocalDateTime());

        User sender = new User();
        sender.setId(rs.getLong("sender_id"));
        transfer.setSender(sender);

        User receiver = new User();
        receiver.setId(rs.getLong("receiver_id"));
        transfer.setReceiver(receiver);

        return transfer;
    }

    @Override
    public List<Transfer> findAll() {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT id, amount, sender_id, receiver_id, date_time FROM transfers ORDER BY date_time DESC";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                transfers.add(mapRowToTransfer(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all transfers", e);
        }

        return transfers;
    }

    @Override
    public Optional<Transfer> findById(long id) {
        String sql = "SELECT id, amount, sender_id, receiver_id, date_time FROM transfers WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToTransfer(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching transfer by id: " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Transfer> save(Transfer entity) {
        String sql = "INSERT INTO transfers (amount, sender_id, receiver_id, date_time) VALUES (?, ?, ?, ?) RETURNING id";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBigDecimal(1, entity.getAmount());
            stmt.setLong(2, entity.getSender().getId());
            stmt.setLong(3, entity.getReceiver().getId());

            if (entity.getTimestamp() != null) {
                stmt.setTimestamp(4, Timestamp.valueOf(entity.getTimestamp()));
            } else {
                stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    entity.setId(rs.getLong("id"));
                    return Optional.of(entity);
                }
            }

        } catch (SQLException ignored) {

        }

        return Optional.empty();
    }

    @Override
    public void update(Transfer entity) {
        String sql = "UPDATE transfers SET amount = ?, sender_id = ?, receiver_id = ?, date_time = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBigDecimal(1, entity.getAmount());
            stmt.setLong(2, entity.getSender().getId());
            stmt.setLong(3, entity.getReceiver().getId());

            if (entity.getTimestamp() != null) {
                stmt.setTimestamp(4, Timestamp.valueOf(entity.getTimestamp()));
            } else {
                stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            }

            stmt.setLong(5, entity.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("No transfer found with id: " + entity.getId());
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error updating transfer with id: " + entity.getId(), e);
        }
    }

    @Override
    public void delete(Transfer entity) {
        deleteById(entity.getId());
    }

    public void deleteById(long id) {
        String sql = "DELETE FROM transfers WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("No transfer found with id: " + id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting transfer with id: " + id, e);
        }
    }

}