package com.example.services;

import com.example.exceptions.NotEnoughMoneyException;
import com.example.exceptions.UserNotAuthenticatedException;
import com.example.exceptions.UserNotFoundException;
import com.example.models.Payment;
import com.example.models.Transfer;
import com.example.models.User;
import com.example.repositories.TransferRepository;
import com.example.repositories.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Optional;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private final UsersRepository usersRepository;
    private final TransferRepository transferRepository;

    private static final BigDecimal ZERO = BigDecimal.ZERO;
    private static final BigDecimal MIN_AMOUNT = new BigDecimal("0.01");

    public PaymentServiceImpl(UsersRepository usersRepository, TransferRepository transferRepository) {
        this.usersRepository = usersRepository;
        this.transferRepository = transferRepository;
    }

    @Override
    public void makePayment(Payment payment) {
        Optional<User> optionalSender = usersRepository.findByLogin(getCurrentUsername());
        Optional<User> optionalReceiver = usersRepository.findByLogin(payment.getReceiver());
        if (optionalSender.isEmpty()) {
            throw new UserNotFoundException("Sender was not found");
        } else if (optionalReceiver.isEmpty()) {
            throw new UserNotFoundException("Receiver was not found");
        }
        User sender = optionalSender.get();
        User receiver = optionalReceiver.get();
        LOGGER.info("Processing payment from {} to user ID {} for amount {}",
                sender.getLogin(), receiver.getLogin(), payment.getAmount());

        validateTransfer(sender, receiver, payment);
        processTransfer(sender, receiver, payment);
    }

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new UserNotAuthenticatedException("User not authenticated");
        }

        return auth.getName();
    }



    private void validateTransfer(User sender, User receiver, Payment payment) {
        BigDecimal amount = payment.getAmount();

        // 1. Проверка на перевод самому себе
        if (sender.getId().equals(receiver.getId())) {
            throw new IllegalArgumentException("Cannot send money to yourself");
        }

        // 2. Проверка положительной суммы (используем compareTo)
        if (amount.compareTo(MIN_AMOUNT) < 0) {
            throw new IllegalArgumentException(
                    String.format("Transfer amount must be at least %s", formatMoney(MIN_AMOUNT))
            );
        }

        // 3. Проверка достаточности средств (compareTo возвращает -1 если balance < amount)
        if (sender.getBalance().compareTo(amount) < 0) {
            throw new NotEnoughMoneyException(
                    String.format("User %s has insufficient funds. Balance: %s, Required: %s",
                            sender.getLogin(),
                            formatMoney(sender.getBalance()),
                            formatMoney(amount))
            );
        }

        // 4. Дополнительная проверка на максимальную сумму (опционально)
        BigDecimal maxAmount = new BigDecimal("1000000.00"); // 1 миллион
        if (amount.compareTo(maxAmount) > 0) {
            throw new IllegalArgumentException(
                    String.format("Transfer amount exceeds maximum limit of %s", formatMoney(maxAmount))
            );
        }
    }

    private void processTransfer(User sender, User receiver, Payment payment) {
        BigDecimal amount = payment.getAmount();

        // Обновляем балансы (BigDecimal иммутабелен, создаем новые объекты)
        BigDecimal newSenderBalance = sender.getBalance().subtract(amount);
        BigDecimal newReceiverBalance = receiver.getBalance().add(amount);

        // Устанавливаем округленные значения
        sender.setBalance(newSenderBalance);
        receiver.setBalance(newReceiverBalance);

        // Сохраняем пользователей
        usersRepository.save(sender);
        usersRepository.save(receiver);

        // Создаем запись о переводе
        Transfer transfer = new Transfer(sender, receiver, amount);
        Optional<Transfer> savedTransfer = transferRepository.save(transfer);

        if (savedTransfer.isPresent()) {
            Long id = savedTransfer.get().getId();
            LOGGER.debug("Transfer {} recorded: {} from {} to {}",
                    id, formatMoney(amount), sender.getLogin(), receiver.getLogin());
        }
    }

    // Вспомогательный метод для форматирования денег
    private String formatMoney(BigDecimal amount) {
        if (amount == null) {
            return "0.00";
        }
        DecimalFormat df = new DecimalFormat("#,##0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(amount);
    }
}